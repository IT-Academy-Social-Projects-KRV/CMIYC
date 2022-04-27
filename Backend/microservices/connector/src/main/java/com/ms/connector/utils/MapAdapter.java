package com.ms.connector.utils;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import java.util.HashMap;
import java.util.Map;

public class MapAdapter extends XmlAdapter<AdaptedMap, Map<String, Object>> {

    @Override
    public Map<String, Object> unmarshal(AdaptedMap adaptedMap) {
        Map<String, Object> map = new HashMap<>();
        for (AdaptedMap.Entry entry : adaptedMap.getEntry()) {
            map.put(entry.getKey(), entry.getValue());
        }

        return map;
    }

    @Override
    public AdaptedMap marshal(Map<String, Object> map) {
        // We won't need this method
        return null;
    }

}
