package com.external.entity;

import com.customstarter.model.Date;
import com.customstarter.model.Name;
import com.customstarter.model.RaceCode;
import com.external.dto.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString

public class Person {

    private Date birthDate;
    private Name name;
    private Gender gender;
    private RaceCode raceCode;
    private String operatorLicenseNumber;

    private boolean isMarried;
    private byte numberOfChildren;
    private String state;
    private String city;
    private String address;

    private String job;
    private String companyName;
    private String phone;
    private String email;

    private String carModel;
    private String operatorLicenseExpirationDate;
    private short carModelYear;
    private String carVin;
    private String carNumber;

    private String image;

}
