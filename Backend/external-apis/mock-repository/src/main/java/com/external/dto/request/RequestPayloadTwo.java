package com.external.dto.request;

import com.external.dto.response.RequestResponseOne;
import com.external.dto.response.RequestResponseTwo;
import com.external.entity.Person;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class RequestPayloadTwo implements RequestPayload {

    private String operatorLicenseNumber;
    private String state;
    private String imageIndicator;


    @Override
    public boolean isEqualToPerson(Person person) {
        return person.getOperatorLicenseNumber().equals(operatorLicenseNumber) && person.getState().equals(state);
    }

    @Override
    public RequestResponseTwo createResponse(Person person) {
        return new RequestResponseTwo(person,"Y".equals(imageIndicator));
    }

}
