package com.external.utils;

import api.soap.xsd.PersonDataModeller;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import java.util.Map;

public class StringObjectMapAdapter extends XmlAdapter<PersonDataModeller, StringObjectMap> {

    @Override
    public StringObjectMap unmarshal(PersonDataModeller data) throws Exception {
        StringObjectMap map = new StringObjectMap();
        for (PersonDataModeller.Entry e : data.getEntry())
        {
            map.put(e.getKey(), e.getValue());
        }
        return map;
    }

    @Override
    public PersonDataModeller marshal(StringObjectMap map) throws Exception {
        PersonDataModeller data = new PersonDataModeller();
        for (Map.Entry<String, Object> entry : map.entrySet())
        {
            PersonDataModeller.Entry newEntry = new PersonDataModeller.Entry();
            newEntry.setKey(entry.getKey());
            newEntry.setValue(entry.getValue());
            data.getEntry().add(newEntry);
        }
        return data;
    }

}
