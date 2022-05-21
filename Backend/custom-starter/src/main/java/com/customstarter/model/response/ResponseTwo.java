package com.customstarter.model.response;

import com.customstarter.model.Name;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
public class ResponseTwo extends Response {

    private Name name;
    private String carModel;
    private String operatorLicenseExpirationDate;
    private short carModelYear;
    private String carVin;
    private String carNumber;
    private String phone;
    private String image;

    public ResponseTwo(
            Name name, String carModel, String operatorLicenseExpirationDate,
            short carModelYear, String carVin, String carNumber,
            String phone, String image
    ) {
        this.name = name;
        this.carModel = carModel;
        this.operatorLicenseExpirationDate = operatorLicenseExpirationDate;
        this.carModelYear = carModelYear;
        this.carVin = carVin;
        this.carNumber = carNumber;
        this.phone = phone;
        this.image = image;
    }
}
