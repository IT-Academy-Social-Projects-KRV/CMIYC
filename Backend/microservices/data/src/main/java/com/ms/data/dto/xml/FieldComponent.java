package com.ms.data.dto.xml;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@Getter
@Setter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Component")
@ToString
public class FieldComponent {

    @XmlAttribute
    private String name;
    @XmlAttribute
    private String type;

    public Field asField(Field parentField) {
        Field componentAsField = new Field();
        componentAsField.setName(getName());
        componentAsField.setType(getType());
        componentAsField.setMaxLength(parentField.getMaxLength());

        return componentAsField;
    }
}
