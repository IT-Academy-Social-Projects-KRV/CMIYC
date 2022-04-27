package com.external.utils;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.util.HashMap;

@XmlJavaTypeAdapter(StringObjectMapAdapter.class)
public class StringObjectMap extends HashMap<String, Object> {}
