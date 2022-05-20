package com.external.dto.response;

import com.external.dto.Name;
import com.external.entity.Person;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseTwo extends Response {

    private Name name;
    private String carModel;
    private String operatorLicenseExpirationDate;
    private short carModelYear;
    private String carVin;
    private String carNumber;
    private String phone;
    private String image;

    public ResponseTwo(Person person, boolean sendImage) {
        super();
        name = person.getName();
        carModel = person.getCarModel();
        operatorLicenseExpirationDate = person.getOperatorLicenseExpirationDate();
        carModelYear = person.getCarModelYear();
        carVin = person.getCarVin();
        carNumber = person.getCarNumber();
        phone = person.getPhone();
        if(sendImage){
            image = person.getImage();
        }
    }


}
