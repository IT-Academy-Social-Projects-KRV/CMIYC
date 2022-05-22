package com.ms.connector.service.api.converter;

import com.customstarter.model.request.SearchRequestPayload;
import com.customstarter.model.response.SearchResponse;
import com.ms.connector.dto.response.SoapApiResponse;
import com.ms.connector.utils.MapAdapter;
import com.ms.connector.utils.SoapHelper;
import com.ms.connector.utils.XMLReaderWithoutNamespace;
import lombok.SneakyThrows;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.stream.XMLStreamReader;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;

public class SoapBodyConverter {

    // TODO: probably should move some of this this data to constructor
    private static final String GS_NAMESPACE_URL = "http://soap.api/xsd";
    private static final String GS_NAMESPACE_PREFIX = "gs";

    private static final String REQUEST_TAG_NAME = "Payload";
    private static final String RESPONSE_TAG_NAME = "SearchResponse";

    private static final MessageFactory messageFactory = SoapHelper.messageFactory;
    private static final SOAPFactory soapFactory = SoapHelper.soapFactory;

    @SneakyThrows
    public String payloadToBody(SearchRequestPayload query) {
        SOAPMessage soapMessage = messageFactory.createMessage();

        SOAPPart soapPart = soapMessage.getSOAPPart();
        SOAPEnvelope envelope = soapPart.getEnvelope();
        SOAPBody body = soapMessage.getSOAPBody();

        envelope.removeNamespaceDeclaration(envelope.getPrefix());
        envelope.addNamespaceDeclaration(GS_NAMESPACE_PREFIX, GS_NAMESPACE_URL);

        Name getPersonRequestName = soapFactory.createName(REQUEST_TAG_NAME, GS_NAMESPACE_PREFIX, GS_NAMESPACE_URL);
        SOAPBodyElement requestElement = body.addBodyElement(getPersonRequestName);
        addTag("operatorLicenseNumber",query.getOperatorLicenseNumber(),requestElement);
        addTag("imageIndicator",String.valueOf(query.isImageIndicator()),requestElement);
        addTag("sexCode",query.getSexCode().name(),requestElement);
        addTag("raceCode",query.getRaceCode().name(),requestElement);
        addTag("state",query.getState().name(),requestElement);
        if(query.getName() != null) {
            Name name = soapFactory.createName("name", GS_NAMESPACE_PREFIX, GS_NAMESPACE_URL);
            SOAPElement nameElement = requestElement.addChildElement(name);
            addTag("first",query.getName().getFirst(),nameElement);
            addTag("middle",query.getName().getMiddle(),nameElement);
            addTag("last",query.getName().getLast(),nameElement);
            addTag("suffix",query.getName().getSuffix(),nameElement);
        }
        if(query.getBirthDate() != null) {
            Name name = soapFactory.createName("birthDate", GS_NAMESPACE_PREFIX, GS_NAMESPACE_URL);
            SOAPElement birthDateElement = requestElement.addChildElement(name);
            addTag("day",String.valueOf(query.getBirthDate().getDay()),birthDateElement);
            addTag("month",String.valueOf(query.getBirthDate().getMonth()),birthDateElement);
            addTag("year",String.valueOf(query.getBirthDate().getYear()),birthDateElement);
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        soapMessage.writeTo(out);

        return out.toString();
    }

    @SneakyThrows
    public SoapApiResponse responseBodyToObject(String response) {
        XMLStreamReader xmlStreamReader = SoapHelper.xmlInputFactory.createXMLStreamReader(new StringReader(response));
        XMLReaderWithoutNamespace xmlReader = new XMLReaderWithoutNamespace(xmlStreamReader);

        xmlReader.nextTag();
        while (!xmlReader.getLocalName()
                .equals(RESPONSE_TAG_NAME)) {
            xmlReader.nextTag();
        }

        JAXBContext jaxbContext = JAXBContext.newInstance(SoapApiResponse.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        unmarshaller.setAdapter(new MapAdapter());

        JAXBElement<SoapApiResponse> jaxbElement = unmarshaller.unmarshal(xmlReader, SoapApiResponse.class);

        xmlReader.close();
        xmlStreamReader.close();


        return jaxbElement.getValue();
    }
    @SneakyThrows
    private void addTag(String key, String value, SOAPElement requestElement){
        if(value == null){
            return;
        }
        Name name = soapFactory.createName(key, GS_NAMESPACE_PREFIX, GS_NAMESPACE_URL);
        SOAPElement childElement = requestElement.addChildElement(name);
        childElement.setValue(value);
    }
}
