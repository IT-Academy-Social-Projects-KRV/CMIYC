package com.customstarter.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Name {

    private String first;
    private String middle;
    private String last;
    private String suffix;

    public boolean isSameName(Name name){
        return (isStringEmpty(first)|| first.equals(name.getFirst())) &&
                (isStringEmpty(middle)|| middle.equals(name.getMiddle())) &&
                (isStringEmpty(last)|| last.equals(name.getLast())) &&
                (isStringEmpty(suffix)|| suffix.equals(name.getSuffix()));
    }
    private boolean isStringEmpty(String string){
        return string == null || string.isEmpty();
    }
}
