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
public class Transaction {

    @XmlAttribute
    private String name;
    @XmlAttribute
    private String version;

    @XmlElementWrapper(name = "Fields")
    @XmlElement(name = "Field")
    private List<Field> fields;

    @XmlElementWrapper(name = "Combinations")
    @XmlElement(name = "Combination")
    private List<Combination> combinations;
}
