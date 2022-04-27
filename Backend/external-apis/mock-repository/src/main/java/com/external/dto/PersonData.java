package com.external.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class PersonData {

    private final int id;
    private final Map<String, Object> data;
}
