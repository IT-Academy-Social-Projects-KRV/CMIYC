package com.ms.connector.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.parsers.DocumentBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapAdapter extends XmlAdapter<MapAdapter.AdaptedMap, Map<String, String>> {

    private final DocumentBuilder documentBuilder;

    public MapAdapter() throws Exception {
        documentBuilder = SoapHelper.documentBuilderFactory.newDocumentBuilder();
    }

    public static class AdaptedMap {
        @XmlAnyElement
        public List<Element> elements = new ArrayList<>();
    }

    @Override
    public AdaptedMap marshal(Map<String, String> map) {
        Document document = documentBuilder.newDocument();
        AdaptedMap adaptedMap = new AdaptedMap();
        for(Map.Entry<String, String> entry : map.entrySet()) {
            Element element = document.createElement(entry.getKey());
            element.setTextContent(entry.getValue());
            adaptedMap.elements.add(element);
        }
        return adaptedMap;
    }

    @Override
    public Map<String, String> unmarshal(AdaptedMap adaptedMap) {
        HashMap<String, String> map = new HashMap<>();
        for(Element element : adaptedMap.elements) {
            map.put(element.getLocalName(), element.getTextContent());
        }
        return map;
    }
}
