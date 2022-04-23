package com.ms.data.service;

import com.google.api.client.http.GenericUrl;
import com.google.api.services.storage.Storage;
import com.google.api.services.storage.model.StorageObject;
import com.ms.data.config.InputStreamContent;
import com.ms.data.model.XmlObject;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

@Service
@RequiredArgsConstructor
public class CloudStorageService {

    private final Storage storage;

    @Value("${cloud-storage.path}")
    private String bucketName;

    public XmlObject getData(String xmldata) throws JAXBException {
        StringReader reader = new StringReader(xmldata);
        JAXBContext context = JAXBContext.newInstance(XmlObject.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        XmlObject xmlObject = (XmlObject) unmarshaller.unmarshal(reader);

        return xmlObject;
    }

    public Boolean uploadFile(MultipartFile file) throws IOException {
        StorageObject object = new StorageObject();
        object.setName(file.getOriginalFilename());
        InputStream targetStream = new ByteArrayInputStream(file.getBytes());
        storage.objects()
                .insert(bucketName, object,
                        new InputStreamContent(file.getOriginalFilename(), targetStream, file.getSize())
                )
                .execute();

        return true;
    }

    public StorageObject download(String fileName) throws IOException {
        StorageObject object = storage.objects()
                .get(bucketName, fileName)
                .execute();
        File file = new File("./" + fileName);
        FileOutputStream os = new FileOutputStream(file);

        storage.getRequestFactory()
                .buildGetRequest(new GenericUrl(object.getMediaLink()))
                .execute()
                .download(os);
        object.set("file", file);

        return object;
    }

}
