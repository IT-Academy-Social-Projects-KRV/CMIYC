package com.external.dto.response;

import com.external.dto.Name;
import com.external.dto.RaceCode;
import com.external.entity.Person;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseOne extends Response {

    private String city;
    private String address;
    private Name name;
    private boolean isMarried;
    private RaceCode raceCode;
    private byte numberOfChildren;
    public ResponseOne(Person person) {
        super();
        city = person.getCity();
        address = person.getAddress();
        name = person.getName();
        isMarried = person.isMarried();
        raceCode = person.getRaceCode();
        numberOfChildren = person.getNumberOfChildren();

    }
}
