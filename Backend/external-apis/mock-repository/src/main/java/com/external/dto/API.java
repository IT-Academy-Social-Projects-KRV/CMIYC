package com.external.dto;

import com.external.dto.request.RequestPayload;
import com.external.dto.request.RequestPayloadOne;
import com.external.dto.request.RequestPayloadThree;
import com.external.dto.request.RequestPayloadTwo;
import com.external.entity.Person;
import lombok.Getter;

import java.util.Map;
import java.util.function.BiConsumer;

@Getter
public enum API {

    API1("api1", RequestPayloadOne.class, (person, map) -> {
        map.put("isbn", person.getIsbn());
        map.put("additionalInfo", person.getAdditionalInfo());
    }),

    API2("api2", RequestPayloadTwo.class, (person, map) -> {
        map.put("location", person.getLocation());
        map.put("carModel", person.getCarModel());
    }),

    API3("api3", RequestPayloadThree.class, (person, map) -> {
        map.put("job", person.getJob());
    });

    private final String name;
    private final Class<?> requestClass;
    private final BiConsumer<Person, Map<String, Object>> loadDataBiConsumer;

    API(String name, Class<?> requestClass, BiConsumer<Person, Map<String, Object>> loadDataBiConsumer) {
        this.name = name;
        this.requestClass = requestClass;
        this.loadDataBiConsumer = loadDataBiConsumer;
    }

    public PersonData createPersonData(Person person, RequestPayload payload) {
        Map<String, Object> data = payload.toMap();
        loadDataBiConsumer.accept(person, data);

        return new PersonData(person.getId(), data);
    }

}