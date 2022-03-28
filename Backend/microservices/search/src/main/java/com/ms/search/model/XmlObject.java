package com.ms.search.model;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "xmlObject")
@XmlRootElement
@Getter
@Setter
public class XmlObject
{
    private String firstName;
    private String lastName;
    private String birthDate;
    private String sex;
    private String foreignDataSource;


}