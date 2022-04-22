package com.ms.connector.dto;

import com.ms.connector.utils.MapAdapter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Map;

@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
@NoArgsConstructor
public class SoapResponse {

    @XmlElement(name = "person")
    @XmlJavaTypeAdapter(MapAdapter.class)
    private Map<String, String> data;
}
