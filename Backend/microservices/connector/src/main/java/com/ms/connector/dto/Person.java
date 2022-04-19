package com.ms.connector.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.HashMap;
import java.util.Map;

@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
@NoArgsConstructor
public class Person {

    private String firstName;
    private String lastName;
    private String birthDayDate;
    private String gender;
    private String additional;

    public Map<String, Object> toMap() {
        Map<String, Object> result = new HashMap<>();
        result.put("firstName", firstName);
        result.put("lastName", lastName);
        result.put("birthDayDate", birthDayDate);
        result.put("gender", gender);
        result.put("additional", additional);

        return result;
    }
}
