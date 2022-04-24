package com.ms.data.controller;

import com.ms.data.model.XmlObject;
import com.ms.data.resource.XmlResource;
import com.ms.data.service.CloudStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.JAXBException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/schemas")
public class DataController {

    @Autowired
    private CloudStorageService cloudStorageService;

    @GetMapping
    public List<XmlObject> xmlSchema() throws JAXBException {
        List<XmlObject> result = new ArrayList<>();
        result.add(cloudStorageService.getData(XmlResource.xmldata1));
        result.add(cloudStorageService.getData(XmlResource.xmldata2));
        result.add(cloudStorageService.getData(XmlResource.xmldata3));

        return result;
    }

    @PostMapping
    public void uploadFile(@RequestParam("file") MultipartFile file) throws IllegalStateException, IOException {
        cloudStorageService.uploadFile(file);
    }

}
