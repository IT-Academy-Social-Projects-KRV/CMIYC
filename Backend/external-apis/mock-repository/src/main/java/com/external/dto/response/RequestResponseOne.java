package com.external.dto.response;

import com.external.entity.Person;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestResponseOne extends RequestResponse {

    private String city;
    private String address;
    public RequestResponseOne( Person person) {
        super();
        city = person.getCity();
        address = person.getAddress();
    }
}
