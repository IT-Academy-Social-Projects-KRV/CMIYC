package com.ms.data.service;

import com.ms.data.model.XmlObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;


@Service

public class XmlService {

    public XmlObject getData(String xmldata) throws JAXBException {
        StringReader reader = new StringReader(xmldata);
        JAXBContext context = JAXBContext.newInstance(XmlObject.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        XmlObject xmlObject = (XmlObject) unmarshaller.unmarshal(reader);


        return xmlObject;

    }

    public XmlService() throws JAXBException {

    }
    public void uploadFile(MultipartFile file) throws IllegalStateException, IOException{
        file.transferTo(new File("C://Users//yahod//Downloads//CMIYC/" + file.getOriginalFilename()));
    }
}
