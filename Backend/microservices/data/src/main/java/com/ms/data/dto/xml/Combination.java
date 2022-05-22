package com.ms.data.dto.xml;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@Getter
@Setter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@ToString
public class Combination {

    @XmlAttribute
    private String keyReference;
    @XmlAttribute
    private String primaryFieldReference;

    @XmlElement(name = "Requirements")
    private Requirements requirements = new Requirements();
}
