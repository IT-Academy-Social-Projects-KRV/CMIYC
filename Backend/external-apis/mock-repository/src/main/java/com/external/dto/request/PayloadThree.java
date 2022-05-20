package com.external.dto.request;

import com.customstarter.model.Date;
import com.customstarter.model.Name;
import com.customstarter.model.response.ResponseThree;
import com.external.entity.Person;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@Setter
@ToString
public class PayloadThree implements Payload {

    private Name name;
    private Date birthDate;
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
        return new ResponseThree(
                person.getName(), person.getCity(), person.getAddress(),
                person.getJob(), person.getCompanyName(), person.getPhone(),
                person.getEmail(), imageIndicator ? person.getImage() : null
        );
    }
}
