package com.ms.data.service;

import com.ms.data.model.XmlObject;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;


@Service

public class XMLService {

    public XmlObject getData(String xmldata) throws JAXBException {
        StringReader reader = new StringReader(xmldata);
        JAXBContext context = JAXBContext.newInstance(XmlObject.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        XmlObject xmlObject = (XmlObject) unmarshaller.unmarshal(reader);

//    List<String> mySet = new ArrayList<String>();
//
//    mySet.add(xmlObject.getFirstName());
//    mySet.add(xmlObject.getLastName());
//    mySet.add(xmlObject.getBirthDate());
//    mySet.add(xmlObject.getSex());
//    mySet.add(xmlObject.getForeignDataSource());
        return xmlObject;

    }

    public XMLService() throws JAXBException {

    }
}
