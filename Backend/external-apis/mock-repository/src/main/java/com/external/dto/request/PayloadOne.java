package com.external.dto.request;

import com.customstarter.model.Date;
import com.customstarter.model.Name;
import com.customstarter.model.RaceCode;
import com.customstarter.model.response.ResponseOne;
import com.external.entity.Person;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class PayloadOne implements Payload {

    private Name name;
    private Date birthDate;
    private String sexCode;
    private RaceCode raceCode;

    @Override
    public boolean isEqualToPerson(Person person) {
        return name != null &&
                name.isSameName(person.getName()) &&
                Objects.equals(person.getBirthDate(), this.birthDate) &&
                person.getGender().isSexCodeCorrect(sexCode) &&
                (raceCode == null || raceCode == person.getRaceCode());
    }

    @Override
    public ResponseOne createResponse(Person person) {
        return new ResponseOne(
                person.getName(), person.getCity(), person.getAddress(),
                person.isMarried(), person.getNumberOfChildren(), person.getRaceCode()
        );
    }
}
