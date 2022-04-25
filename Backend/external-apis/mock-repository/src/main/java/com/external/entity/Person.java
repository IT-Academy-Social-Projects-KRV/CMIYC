package com.external.entity;

import com.external.dto.Gender;
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

    private int id;

    private String firstName;
    private String lastName;
    private String birthDayDate;
    private Gender gender;

    private String country;
    private String city;
    private String address;

    private String phone;
    private String email;
    private String job;

    private String carModel;
    private String carVin;
}
