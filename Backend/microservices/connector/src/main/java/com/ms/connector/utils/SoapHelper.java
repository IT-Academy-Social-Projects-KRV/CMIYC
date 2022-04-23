package com.ms.connector.utils;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.stream.XMLInputFactory;

public abstract class SoapHelper {

    public static final MessageFactory messageFactory;
    public static final SOAPFactory soapFactory;
    public static final XMLInputFactory xmlInputFactory;
    public static final DocumentBuilderFactory documentBuilderFactory;

    static {
        try {
            soapFactory = SOAPFactory.newInstance();
            messageFactory = MessageFactory.newInstance();
            xmlInputFactory = XMLInputFactory.newFactory();
            documentBuilderFactory = DocumentBuilderFactory.newInstance();
        } catch (SOAPException e) {
            throw new RuntimeException(e);
        }
    }
}
