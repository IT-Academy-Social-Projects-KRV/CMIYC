package com.ms.connector.service.api;

import com.ms.connector.model.SearchQuery;
import com.ms.connector.service.client.HttpClient;
import com.ms.connector.service.client.SoapClient;
import com.ms.connector.utils.SoapHelper;
import lombok.SneakyThrows;
import org.jsoup.Connection;

import javax.xml.soap.*;
import java.io.ByteArrayOutputStream;

public class SoapApiConnection extends HttpApiConnection {

    // TODO: probably should move some of this this data to constructor
    private static final String GS_NAMESPACE_URL = "http://soap.api/xsd";
    private static final String GS_NAMESPACE_PREFIX = "gs";
    private static final String REQUEST_NAME = "getPersonRequest";

    private static final MessageFactory messageFactory = SoapHelper.messageFactory;
    private static final SOAPFactory soapFactory = SoapHelper.soapFactory;

    private final SoapClient client;

    public SoapApiConnection(Connection.Method method, String path) {
        super(method, path);
        this.client = new SoapClient(path);
    }

    @Override
    protected HttpClient getClient() {
        return client;
    }

    @SneakyThrows
    @Override
    protected String prepareRequestBody(SearchQuery query) {
        SOAPMessage soapMessage = messageFactory.createMessage();

        SOAPPart soapPart = soapMessage.getSOAPPart();
        SOAPEnvelope envelope = soapPart.getEnvelope();
        SOAPBody body = soapMessage.getSOAPBody();

        envelope.removeNamespaceDeclaration(envelope.getPrefix());
        envelope.addNamespaceDeclaration(GS_NAMESPACE_PREFIX, GS_NAMESPACE_URL);

        Name getPersonRequestName = soapFactory.createName(REQUEST_NAME, GS_NAMESPACE_PREFIX, GS_NAMESPACE_URL);
        SOAPBodyElement requestElement = body.addBodyElement(getPersonRequestName);

        convertQueryToMap(query)
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
}
