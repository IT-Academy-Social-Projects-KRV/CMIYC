package com.external.dto.request;

import com.external.dto.response.Response;
import com.external.entity.Person;

public interface Payload {

    boolean isEqualToPerson(Person person);

    Response createResponse(Person person);

}
