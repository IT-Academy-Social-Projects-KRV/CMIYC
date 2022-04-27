package com.external.dto.request;

import com.external.entity.Person;

import java.util.Map;

public interface RequestPayload {

    boolean isEqualToPerson(Person person);

    Map<String, Object> toMap();

}
