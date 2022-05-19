package com.external.dto.request;

import com.customstarter.model.response.Response;
import com.external.entity.Person;

public interface Payload {

    boolean isEqualToPerson(Person person);

    Response createResponse(Person person);

}
