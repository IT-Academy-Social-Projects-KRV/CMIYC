package com.ms.connector.utils;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class AdaptedMap {

    private List<Entry> entry = new ArrayList<>();

    @Getter
    @Setter
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Entry {

        private String key;
        @XmlJavaTypeAdapter(TagToObjectAdapter.class)
        private Object value;

        @Getter
        @Setter
        @ToString
        @XmlAccessorType(XmlAccessType.FIELD)
        public static class Value {

            @XmlAttribute(name = "type")
            private String type;
            @XmlValue
            private String value;

        }

    }

}