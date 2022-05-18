package com.ms.data.dto.xml;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@ToString
public class Field {

    @XmlAttribute
    private String name;
    @XmlAttribute
    private String type;
    @XmlAttribute
    private String reference;
    @XmlAttribute
    private Integer maxLength;
    @XmlAttribute
    private String description;

    @XmlElementWrapper(name = "Components")
    @XmlElement(name = "Component")
    private List<FieldComponent> components;
}


