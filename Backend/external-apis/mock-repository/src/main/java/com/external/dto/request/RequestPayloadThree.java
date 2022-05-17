package com.external.dto.request;

import com.external.dto.Date;
import com.external.dto.Gender;
import com.external.dto.Name;
import com.external.dto.response.RequestResponseThree;
import com.external.dto.response.RequestResponseTwo;
import com.external.entity.Person;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@ToString
public class RequestPayloadThree implements RequestPayload {

    private Date birthDate;
    private Name name;
    private String sexCode;
    private String state;
    private String imageIndicator;
    @Override

    public boolean isEqualToPerson(Person person) {
        return person.getName().equals(name) && person.getBirthDate().equals(birthDate) && person.getGender().isSexCodeCorrect(sexCode) && person.getState().equals(state);
    }


    @Override
    public RequestResponseThree createResponse(Person person) {
        return new RequestResponseThree(person,"Y".equals(imageIndicator));
    }

}
