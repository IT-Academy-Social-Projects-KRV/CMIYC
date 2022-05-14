package com.ms.data.dto.xml;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Getter
@Setter
@NoArgsConstructor
@XmlType(name = "InterfaceSchema")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@ToString
public class InterfaceSchema {

    @XmlElement(name = "Transaction")
    private Transaction transaction;
}
