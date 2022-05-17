package com.external.dto;

import com.external.dto.request.RequestPayload;
import com.external.dto.request.RequestPayloadOne;
import com.external.dto.request.RequestPayloadThree;
import com.external.dto.request.RequestPayloadTwo;
import com.external.dto.response.RequestResponse;
import com.external.entity.Person;
import lombok.Getter;

import java.util.Map;
import java.util.function.BiConsumer;

@Getter
public enum API {

    API1("api1", RequestPayloadOne.class),

    API2("api2", RequestPayloadTwo.class),

    API3("api3", RequestPayloadThree.class);

    private final String name;
    private final Class<?> requestClass;

    API(String name, Class<?> requestClass) {
        this.name = name;
        this.requestClass = requestClass;
    }

    public RequestResponse createPersonData(Person person, RequestPayload payload) {
        RequestResponse response = payload.createResponse(person);
        response.setApiName(this.name);
        return response;
    }

}
