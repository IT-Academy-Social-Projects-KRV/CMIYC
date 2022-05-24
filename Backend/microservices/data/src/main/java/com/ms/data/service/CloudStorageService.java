package com.ms.data.service;

import com.google.api.client.http.GenericUrl;
import com.google.api.services.storage.Storage;
import com.google.api.services.storage.model.Objects;
import com.google.api.services.storage.model.StorageObject;
import com.ms.data.config.InputStreamContent;
import com.ms.data.dto.SchemaFile;
import com.customstarter.model.schema.Schema;
import com.ms.data.dto.xml.InterfaceSchema;
import com.ms.data.exception.CurrentSelectedSchemaException;
import com.ms.data.exception.InvalidSchemaException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;

import java.io.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CloudStorageService {

    private static final String CHECKED_SCHEMA_METADATA_MAP_KEY = "checked";

    private final Storage storage;
    private final XmlReaderService xmlReaderService;
    private final SearchFormBuilderService searchFormBuilderService;

    @Value("${cloud-storage.path}")
    private String bucketName;
    private String selectedSchemaName;

    @PostConstruct
    public void initSelectedSchema() {
        for (SchemaFile schemaFile : getAll()) {
            if(schemaFile.isSelected()) {
                selectedSchemaName = schemaFile.getName();
                break;
            }
        }
    }

    @SneakyThrows
    public void uploadSchema(MultipartFile file) {
        try {
            String fileContent = new String(file.getBytes());
            xmlReaderService.read(fileContent);
        } catch (Exception e) {
            throw new InvalidSchemaException();
        }

        StorageObject object = new StorageObject();

        String originalFilename = file.getOriginalFilename() == null ?
                "schema.xml" : file.getOriginalFilename();

        String name = createUniqueNameForNewSchema(originalFilename);
        object.setName(name);

        InputStream targetStream = new ByteArrayInputStream(file.getBytes());
        storage.objects()
                .insert(bucketName, object,
                        new InputStreamContent(file.getOriginalFilename(), targetStream, file.getSize())
                )
                .execute();


    }

    @SneakyThrows
    public List<SchemaFile> getAll() {
        Objects list = storage.objects().list(bucketName).execute();

        return list.getItems()
                .stream()
                .map(this::createSchemaFile)
                .sorted(Comparator.comparing(SchemaFile::getUploadedAt).reversed())
                .collect(Collectors.toList());
    }

    @SneakyThrows
    public Optional<SchemaFile> getOne(String name) {
        try {
            StorageObject object = getStorageObjectByName(name);
            return Optional.of(this.createSchemaFile(object));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<String> getFileContent(String name) {
        try {
            StorageObject storageObject = getStorageObjectByName(name);
            return Optional.ofNullable(getFileContent(storageObject));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public InterfaceSchema getInterfaceSchema(String name) {
        Optional<String> fileContent = getFileContent(name);
        return fileContent.map(xmlReaderService::read).orElseThrow();
    }

    public Schema readSchema(String name) {
        InterfaceSchema interfaceSchema = getInterfaceSchema(name);
        return searchFormBuilderService.buildSchemaForm(interfaceSchema);
    }

    @SneakyThrows
    public void selectSchema(String name) {
        if(name.equals(selectedSchemaName))
            return;

        getInterfaceSchema(name);

        StorageObject schemaToSelect = getStorageObjectByName(name);
        if(!isSchemaSelected(schemaToSelect)) {
            schemaToSelect.setMetadata(Collections.singletonMap(CHECKED_SCHEMA_METADATA_MAP_KEY, ""));
            storage.objects()
                    .update(bucketName, schemaToSelect.getName(), schemaToSelect)
                    .execute();
        }

        String previousSchemaName = selectedSchemaName;
        selectedSchemaName = name;
        if(previousSchemaName == null || selectedSchemaName.equals(previousSchemaName))
            return;

        StorageObject previousSelectedSchema = getStorageObjectByName(previousSchemaName);
        if (isSchemaSelected(previousSelectedSchema)) {
            previousSelectedSchema.setMetadata(Collections.emptyMap());
            storage.objects()
                    .update(bucketName, previousSchemaName, previousSelectedSchema)
                    .execute();
        }
    }

    public Schema getSelectedSchema() {
        if (selectedSchemaName == null) {
            return null;
        }

        return readSchema(selectedSchemaName);
    }

    public void deleteSchema(String name) throws IOException {
        if (selectedSchemaName.equals(name)) {
            throw new CurrentSelectedSchemaException("you cant delete current schema");
        }
        storage.objects().delete(bucketName, name).execute();

    }

    @SneakyThrows
    private StorageObject getStorageObjectByName(String name) {
        return storage.objects().get(bucketName, name).execute();
    }

    @SneakyThrows
    private String getFileContent(StorageObject object) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        storage.getRequestFactory()
                .buildGetRequest(new GenericUrl(object.getMediaLink()))
                .execute()
                .download(outputStream);

        return outputStream.toString();
    }

    private SchemaFile createSchemaFile(StorageObject object) {
        LocalDateTime updatedAt = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(object.getTimeCreated().getValue()),
                TimeZone.getDefault().toZoneId()
        );

        return new SchemaFile(
                object.getName(),
                this.isSchemaSelected(object),
                updatedAt
        );
    }

    private boolean isSchemaSelected(StorageObject object) {
        return object.getMetadata() != null &&
                object.getMetadata().containsKey(CHECKED_SCHEMA_METADATA_MAP_KEY);
    }

    private String createUniqueNameForNewSchema(String originalFilename) {
        if(!originalFilename.contains(".")) {
            originalFilename += ".xml";
        }

        String name = originalFilename.substring(0, originalFilename.lastIndexOf("."));
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

        int number = 0;
        String newName = originalFilename;
        while (schemaExists(newName)) {
            newName = String.format("%s (%d)%s", name, ++number, extension);
        }

        return newName;
    }

    private boolean schemaExists(String newName) {
        return getOne(newName).isPresent();
    }
}
