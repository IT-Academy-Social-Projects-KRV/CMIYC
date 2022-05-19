package com.external.dto.request;

import com.external.dto.Date;
import com.external.dto.Name;
import com.external.dto.RaceCode;
import com.external.dto.response.ResponseOne;
import com.external.entity.Person;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class PayloadOne implements Payload {

    private Date birthDate;
    private Name name;
    private String sexCode;
    private RaceCode raceCode;


    @Override

    public boolean isEqualToPerson(Person person) {
        return name != null &&
                name.isSameName(person.getName())&&
                Objects.equals(person.getBirthDate(),this.birthDate) &&
                person.getGender().isSexCodeCorrect(sexCode)&&
                (raceCode == null || raceCode == person.getRaceCode());
    }

    @Override
    public ResponseOne createResponse(Person person) {
   return new ResponseOne(person);
    }

}
