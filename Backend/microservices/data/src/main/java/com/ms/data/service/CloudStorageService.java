package com.ms.data.service;

import com.google.api.client.http.GenericUrl;
import com.google.api.services.storage.Storage;
import com.google.api.services.storage.model.Objects;
import com.google.api.services.storage.model.StorageObject;
import com.ms.data.config.InputStreamContent;
import com.ms.data.dto.SchemaFile;
import com.ms.data.dto.xml.InterfaceSchema;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CloudStorageService {

    private final Storage storage;
    private final XmlReaderService xmlReaderService;

    @Value("${cloud-storage.path}")
    private String bucketName;

    @SneakyThrows
    public void uploadSchema(MultipartFile file) {
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
            StorageObject object = storage.objects().get(bucketName, name).execute();
            return Optional.of(this.createSchemaFile(object));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<String> getFileContent(String name) {
        try {
            StorageObject storageObject = storage.objects().get(bucketName, name).execute();
            return Optional.ofNullable(getFileContent(storageObject));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public InterfaceSchema getInterfaceSchema(String name) {
        Optional<String> fileContent = getFileContent(name);
        return fileContent.map(xmlReaderService::read).orElseThrow();
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
                Instant.ofEpochMilli(object.getUpdated().getValue()),
                TimeZone.getDefault().toZoneId()
        );

        return new SchemaFile(
                object.getName(),
                this.isSchemaSelected(object),
                updatedAt
        );
    }

    // Will implement logic later
    private boolean isSchemaSelected(StorageObject object) {
        return false;
    }

    private String createUniqueNameForNewSchema(String originalFilename) {
        String name = originalFilename;
        String extension = "";
        if(originalFilename.contains(".")) {
            name = originalFilename.substring(0, originalFilename.lastIndexOf("."));
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

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
