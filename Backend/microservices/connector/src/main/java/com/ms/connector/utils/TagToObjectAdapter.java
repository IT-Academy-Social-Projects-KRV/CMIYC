package com.ms.connector.utils;

import com.ms.connector.exception.XMLTypeMapperNotFoundException;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import lombok.Getter;

import java.util.function.Function;

public class TagToObjectAdapter extends XmlAdapter<AdaptedMap.Entry.Value, Object> {

    @Getter
    private enum XMLTypeMapper {

        STRING(s -> s, "xs:string"),
        INTEGER(Integer::parseInt, "xs:int", "xs:integer"),
        LONG(Long::parseLong, "xs:long"),
        DOUBLE(Double::parseDouble, "xs:decimal", "xs:double"),
        FLOAT(Float::parseFloat, "xs:float"),
        BOOLEAN(Boolean::parseBoolean, "xs:boolean");

        private final String[] xmlTypes;
        private final Function<String, Object> mapperFunction;

        private boolean supports(String type) {
            for (String xmlType : this.xmlTypes) {
                if (xmlType.equals(type)) {
                    return true;
                }
            }

            return false;
        }

        Object createObject(String value) {
            return this.mapperFunction.apply(value);
        }

        XMLTypeMapper(Function<String, Object> mapperFunction, String... xmlTypes) {
            this.mapperFunction = mapperFunction;
            this.xmlTypes = xmlTypes;
        }
    }

    @Override
    public Object unmarshal(AdaptedMap.Entry.Value value) {
        for (XMLTypeMapper xmlTypeMapper : XMLTypeMapper.values()) {
            if (xmlTypeMapper.supports(value.getType())) {
                return xmlTypeMapper.createObject(value.getValue());
            }
        }

        throw new XMLTypeMapperNotFoundException(value.getType());
    }

    @Override
    public AdaptedMap.Entry.Value marshal(Object o) {
        // We won't need this method
        return null;
    }

}
