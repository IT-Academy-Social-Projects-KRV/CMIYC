package com.ms.data.controller;

import com.ms.data.model.XmlObject;
import com.ms.data.resource.XmlResource;
import com.ms.data.service.XmlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.JAXBException;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/data")
public class DataController {

    @Autowired
    private XmlService xmlService;

    @GetMapping
    public List<XmlObject> welcomeFromData() throws JAXBException {
        List<XmlObject> result = new ArrayList<XmlObject>();
        result.add(xmlService.getData(XmlResource.xmldata1));
        result.add(xmlService.getData(XmlResource.xmldata2));
        result.add(xmlService.getData(XmlResource.xmldata3));
        return result;
    }

}
