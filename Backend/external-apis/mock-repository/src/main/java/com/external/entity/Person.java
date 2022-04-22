package com.external.entity;

import com.external.dto.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Person {

    private int id;
    private String firstName;
    private String lastName;
    private String birthDayDate;
    private Gender gender;
    private String additionalInfo;
    private String carModel;
    private String isbn;
    private String job;
    private String location;

}
