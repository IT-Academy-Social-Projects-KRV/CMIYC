package com.customstarter.model.response;

import com.customstarter.model.Name;
import com.customstarter.model.RaceCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseOne extends Response {

    private Name name;
    private String city;
    private String address;
    private boolean isMarried;
    private byte numberOfChildren;
    private RaceCode raceCode;

    public ResponseOne(
            Name name, String city, String address,
            boolean isMarried, byte numberOfChildren, RaceCode raceCode
    ) {
        this.name = name;
        this.city = city;
        this.address = address;
        this.isMarried = isMarried;
        this.numberOfChildren = numberOfChildren;
        this.raceCode = raceCode;
    }
}
