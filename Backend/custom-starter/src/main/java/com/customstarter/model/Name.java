package com.customstarter.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Name {

    private String first;
    private String middle;
    private String last;
    private String suffix;
}
