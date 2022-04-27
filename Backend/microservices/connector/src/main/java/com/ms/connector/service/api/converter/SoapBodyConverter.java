package com.ms.connector.service.api.converter;

import com.ms.connector.dto.SearchQuery;
import com.ms.connector.dto.SearchResponse;
import com.ms.connector.utils.MapAdapter;
import com.ms.connector.utils.SoapHelper;
import com.ms.connector.utils.XMLReaderWithoutNamespace;
import lombok.SneakyThrows;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.soap.*;
import javax.xml.stream.XMLStreamReader;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;

public class SoapBodyConverter implements BodyConverter {

    // TODO: probably should move some of this this data to constructor
    private static final String GS_NAMESPACE_URL = "http://soap.api/xsd";
    private static final String GS_NAMESPACE_PREFIX = "gs";

    private static final String REQUEST_TAG_NAME = "SearchRequest";
    private static final String RESPONSE_TAG_NAME = "SearchResponse";

    private static final MessageFactory messageFactory = SoapHelper.messageFactory;
    private static final SOAPFactory soapFactory = SoapHelper.soapFactory;

    @SneakyThrows
    @Override
    public String queryToRequestBody(SearchQuery query) {
        SOAPMessage soapMessage = messageFactory.createMessage();

        SOAPPart soapPart = soapMessage.getSOAPPart();
        SOAPEnvelope envelope = soapPart.getEnvelope();
        SOAPBody body = soapMessage.getSOAPBody();

        envelope.removeNamespaceDeclaration(envelope.getPrefix());
        envelope.addNamespaceDeclaration(GS_NAMESPACE_PREFIX, GS_NAMESPACE_URL);

        Name getPersonRequestName = soapFactory.createName(REQUEST_TAG_NAME, GS_NAMESPACE_PREFIX, GS_NAMESPACE_URL);
        SOAPBodyElement requestElement = body.addBodyElement(getPersonRequestName);

        query.toMap()
                .forEach((key, value) -> {
                    try {
                        Name name = soapFactory.createName(key, GS_NAMESPACE_PREFIX, GS_NAMESPACE_URL);
                        SOAPElement childElement = requestElement.addChildElement(name);
                        childElement.setValue(value);
                    } catch (SOAPException e) {
                        throw new RuntimeException(e);
                    }
                });

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        soapMessage.writeTo(out);

        return out.toString();
    }

    @SneakyThrows
    @Override
    public SearchResponse responseBodyToObject(String response) {
        XMLStreamReader xmlStreamReader = SoapHelper.xmlInputFactory.createXMLStreamReader(new StringReader(response));
        XMLReaderWithoutNamespace xmlReader = new XMLReaderWithoutNamespace(xmlStreamReader);

        xmlReader.nextTag();
        while (!xmlReader.getLocalName()
                .equals(RESPONSE_TAG_NAME)) {
            xmlReader.nextTag();
        }

        JAXBContext jaxbContext = JAXBContext.newInstance(SearchResponse.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        unmarshaller.setAdapter(new MapAdapter());

        JAXBElement<SearchResponse> jaxbElement = unmarshaller.unmarshal(xmlReader, SearchResponse.class);

        xmlReader.close();
        xmlStreamReader.close();

        return jaxbElement.getValue();
    }
}
