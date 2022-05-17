package com.external.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode
@Getter
@Setter
@ToString
public class Name {
    private String first;
    private String middle;
    private String last;
    private String suffix;

}
