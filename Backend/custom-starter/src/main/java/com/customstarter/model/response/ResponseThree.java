package com.customstarter.model.response;

import com.customstarter.model.Name;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseThree extends Response {

    private Name name;
    private String city;
    private String address;
    private String job;
    private String companyName;
    private String phone;
    private String email;
    private String image;

    public ResponseThree(
            Name name, String city, String address,
            String job, String companyName, String phone,
            String email, String image
    ) {
        this.name = name;
        this.city = city;
        this.address = address;
        this.job = job;
        this.companyName = companyName;
        this.phone = phone;
        this.email = email;
        this.image = image;
    }
}
