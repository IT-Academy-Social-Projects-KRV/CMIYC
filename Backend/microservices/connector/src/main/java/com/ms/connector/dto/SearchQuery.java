package com.ms.connector.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchQuery {

    private String firstName;
    private String lastName;
    private String birthDayDate;
    private String sex;
    private Set<String> foreignDataSource = new HashSet<>();

    public Map<String, String> toMap() {
        Map<String, String> data = new HashMap<>();
        data.put("firstName", firstName);
        data.put("lastName", lastName);
        data.put("birthDayDate", birthDayDate);
        // TODO: rename field in this class or in mock repository
        data.put("gender", sex.toUpperCase());

        while (data.containsValue(null))
            data.values().remove(null);

        return data;
    }
}
