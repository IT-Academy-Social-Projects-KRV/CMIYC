package com.ms.authority.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
@Getter
public class FrontendData {

    private String email;
    private String fullName;

    public void loadToMap(Map<String, Object> data) {
        data.put("email", email);
        data.put("fullName", fullName);
    }
}
