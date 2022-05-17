package com.external.dto.request;

import com.external.dto.response.RequestResponse;
import com.external.entity.Person;

public interface RequestPayload {

    boolean isEqualToPerson(Person person);

    RequestResponse createResponse(Person person);

}
