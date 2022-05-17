package com.external.dto.request;

import com.external.dto.Date;
import com.external.dto.Gender;
import com.external.dto.Name;
import com.external.dto.response.RequestResponse;
import com.external.dto.response.RequestResponseOne;
import com.external.entity.Person;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class RequestPayloadOne implements RequestPayload {

    private Date birthDate;
    private Name name;
    private String sexCode;


    @Override

    public boolean isEqualToPerson(Person person) {
        return person.getName().equals(name) && person.getBirthDate().equals(birthDate) && person.getGender().isSexCodeCorrect(sexCode);
    }

    @Override
    public RequestResponseOne createResponse(Person person) {
   return new RequestResponseOne(person);
    }

}
