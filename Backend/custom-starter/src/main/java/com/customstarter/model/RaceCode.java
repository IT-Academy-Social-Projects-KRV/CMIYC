package com.customstarter.model;

import lombok.Getter;

@Getter
public enum RaceCode {

    W("White"),
    B("Black or African American"),
    A("Asian"),
    L("Latino or Hispanic"),
    I("American Indian or Alaska Native"),
    H("Native Hawaiian and Other Pacific Islander"),
    O("Other");

    private final String race;

    RaceCode(String race) {
        this.race = race;
    }
}
