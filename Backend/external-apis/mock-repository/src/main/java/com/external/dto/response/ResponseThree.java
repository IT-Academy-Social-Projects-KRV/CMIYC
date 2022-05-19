package com.external.dto.response;

import com.external.dto.Name;
import com.external.entity.Person;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseThree extends Response {

    private String city;
    private String address;
    private String job;
    private String companyName;
    private String phone;
    private String email;
    private String image;
    private Name name;

    public ResponseThree(Person person, boolean sendImage) {
        super();
        city = person.getCity();
        address = person.getAddress();
        job = person.getJob();
        companyName = person.getCompanyName();
        phone = person.getPhone();
        email = person.getEmail();
        name = person.getName();
        if (sendImage) {
            image = person.getImage();
        }
    }
}
