package com.external.dto.request;

import com.external.entity.Person;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class RequestPayloadOne implements RequestPayload {

    private String firstName;

    @Override
    public boolean isEqualToPerson(Person person) {
        return person.getFirstName()
                .equals(firstName);
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("firstName", firstName);

        return map;
    }

}
