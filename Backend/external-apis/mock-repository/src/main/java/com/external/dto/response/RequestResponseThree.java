package com.external.dto.response;

import com.external.entity.Person;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestResponseThree extends RequestResponse {

    private String carModel;
    private String email;
    private String image;

    public RequestResponseThree( Person person, boolean sendImage) {
        super();
        email = person.getEmail();
        carModel = person.getCarModel();
        if (sendImage) {
            image = person.getImage();


        }
    }
}
