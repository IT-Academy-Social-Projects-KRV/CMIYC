package com.external.dto.request;

import com.external.dto.response.ResponseTwo;
import com.external.entity.Person;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class PayloadTwo implements Payload {

    private String operatorLicenseNumber;
    private String state;
    private boolean imageIndicator;


    @Override
    public boolean isEqualToPerson(Person person) {
        return  Objects.equals(person.getOperatorLicenseNumber(),this.operatorLicenseNumber) &&
                Objects.equals(person.getState(),this.state);
    }

    @Override
    public ResponseTwo createResponse(Person person) {
        return new ResponseTwo(person,imageIndicator);
    }
}
