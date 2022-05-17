package com.external.entity;

import com.external.dto.Date;
import com.external.dto.Gender;
import com.external.dto.Name;
import lombok.AllArgsConstructor;
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
    private String state;
    private String operatorLicenseNumber;

    private String city;
    private String address;

    private String job;
    private String phone;

    private String carModel;
    private String email;





    private String image;














}
