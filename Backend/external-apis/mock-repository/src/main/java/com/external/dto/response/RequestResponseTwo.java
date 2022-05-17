package com.external.dto.response;

import com.external.entity.Person;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestResponseTwo extends RequestResponse{

    private String job;
    private String phone;
    private String image;
    public RequestResponseTwo( Person person,boolean sendImage) {
        super();
        job = person.getJob();
        phone = person.getPhone();
        if(sendImage){
            image = person.getImage();
        }
    }


}
