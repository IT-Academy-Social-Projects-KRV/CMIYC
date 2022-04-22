package com.external.dto.request;

import com.external.dto.Gender;
import com.external.entity.Person;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class RequestPayloadThree extends RequestPayloadTwo {

    private String birthDayDate;
    private Gender gender = Gender.UNKNOWN;

    @Override
    public boolean isEqualToPerson(Person person) {
        return super.isEqualToPerson(person) && person.getBirthDayDate()
                .equals(birthDayDate) && (gender == Gender.UNKNOWN || gender == person.getGender());
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = super.toMap();
        map.put("birthDayDate", birthDayDate);
        map.put("gender", gender);

        return map;
    }

}
