package com.customstarter.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@XmlAccessorType(XmlAccessType.FIELD)
public class Date {

    private int day;
    private int month;
    private int year;

}
