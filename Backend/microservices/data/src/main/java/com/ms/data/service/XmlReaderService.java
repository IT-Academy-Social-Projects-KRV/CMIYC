package com.ms.data.service;

import com.ms.data.dto.xml.InterfaceSchema;
import com.ms.data.exception.BadXmlSchemaException;
import com.ms.data.utils.XMLReaderWithoutNamespace;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import java.io.StringReader;

@Service
public class XmlReaderService {

    private static final XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();

    @SneakyThrows
    public InterfaceSchema read(String xml) {
        XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(new StringReader(xml));
        XMLReaderWithoutNamespace xmlReader = new XMLReaderWithoutNamespace(xmlStreamReader);

        JAXBContext jaxbContext = JAXBContext.newInstance(InterfaceSchema.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        JAXBElement<InterfaceSchema> jaxbElement = unmarshaller.unmarshal(xmlReader, InterfaceSchema.class);

        xmlReader.close();
        xmlStreamReader.close();

        InterfaceSchema value = jaxbElement.getValue();
        if(value != null && value.getTransaction() == null) {
            throw new BadXmlSchemaException();
        }

        return value;
    }
}
