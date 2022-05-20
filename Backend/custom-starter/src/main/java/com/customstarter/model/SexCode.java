package com.customstarter.model;

import lombok.Getter;

@Getter
public enum SexCode {

    M("Male"), F("Female");

    private final String sex;

    SexCode(String sex) {
        this.sex = sex;
    }
}
