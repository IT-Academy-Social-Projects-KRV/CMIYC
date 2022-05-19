package com.external.dto;

public enum Gender {

    MALE,
    FEMALE;

    public boolean isSexCodeCorrect(String sexCode){
        return this.name().substring(0, 1).equals(sexCode);
    }
}
