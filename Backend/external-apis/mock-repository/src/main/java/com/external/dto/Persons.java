package com.external.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Persons {
    private String firstName;
    private String lastName;
    private String birthDayDate;
    private Gender gender;
    private String additional;

    @Override
    public String toString() {
        return "\"[FirstName= " + firstName + ",Lastname= "
                + lastName +",BirthdayDate= "+birthDayDate+",gender = "+gender+"additional= "+additional+"]";
    }
}
