package com.ms.connector.utils;

import lombok.Getter;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;

public abstract class SoapHelper {

    public static final MessageFactory messageFactory;
    public static final SOAPFactory soapFactory;

    static {
        try {
            soapFactory = SOAPFactory.newInstance();
            messageFactory = MessageFactory.newInstance();
        } catch (SOAPException e) {
            throw new RuntimeException(e);
        }
    }
}
