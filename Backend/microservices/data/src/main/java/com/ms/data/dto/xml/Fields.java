package com.ms.data.dto.xml;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@ToString
public class Fields {

    @XmlElement(name = "Field")
    private List<Field> mandatoryFields = new ArrayList<>();

    @XmlElementWrapper(name = "Any")
    @XmlElement(name = "Field")
    private List<Field> optionalFields = new ArrayList<>();
}
