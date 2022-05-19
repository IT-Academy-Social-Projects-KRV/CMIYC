package com.external.dto;

import com.customstarter.model.response.Response;
import com.external.dto.request.Payload;
import com.external.dto.request.PayloadOne;
import com.external.dto.request.PayloadThree;
import com.external.dto.request.PayloadTwo;
import com.external.entity.Person;
import lombok.Getter;

@Getter
public enum API {

    API1("api1", PayloadOne.class),
    API2("api2", PayloadTwo.class),
    API3("api3", PayloadThree.class);

    private final String name;
    private final Class<?> requestClass;

    API(String name, Class<?> requestClass) {
        this.name = name;
        this.requestClass = requestClass;
    }

    public Response createPersonData(Person person, Payload payload) {
        Response response = payload.createResponse(person);
        response.setApiName(this.name);
        return response;
    }
}
