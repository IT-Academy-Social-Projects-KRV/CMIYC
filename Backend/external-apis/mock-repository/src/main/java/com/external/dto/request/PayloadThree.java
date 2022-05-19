package com.external.dto.request;

import com.external.dto.Date;
import com.external.dto.Name;
import com.external.dto.response.ResponseThree;
import com.external.entity.Person;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@Setter
@ToString
public class PayloadThree implements Payload {
    private Date birthDate;
    private Name name;
    private String state;
    private boolean imageIndicator;
    @Override

    public boolean isEqualToPerson(Person person) {

        return name != null &&
                name.isSameName(person.getName()) &&
                Objects.equals(person.getBirthDate(),this.birthDate) &&
                Objects.equals(person.getState(),this.state);
    }


    @Override
    public ResponseThree createResponse(Person person) {
        return new ResponseThree(person,imageIndicator);
    }

}
