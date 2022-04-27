package com.external.dto.request;

import com.external.entity.Person;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class RequestPayloadTwo extends RequestPayloadOne {

    private String lastName;

    @Override
    public boolean isEqualToPerson(Person person) {
        return super.isEqualToPerson(person) && person.getLastName()
                .equals(lastName);
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = super.toMap();
        map.put("lastName", lastName);

        return map;
    }

}
