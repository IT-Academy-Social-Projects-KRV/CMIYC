package com.ms.data.service;

import com.ms.data.dto.form.JsonForm;
import com.ms.data.dto.xml.Field;
import com.ms.data.dto.xml.InterfaceSchema;
import com.ms.data.service.builders.JsonInputBuilder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class JsonFormBuilderService {

    private final Map<String, JsonInputBuilder> builders = new HashMap<>();

    public void registerInputBuilder(String fieldType, JsonInputBuilder builder) {
        this.builders.put(fieldType, builder);
    }

    public JsonForm build(InterfaceSchema schema) {
        JsonForm jsonForm = new JsonForm();
        jsonForm.setName(schema.getTransaction().getName());

        for (Field field : schema.getTransaction().getFields()) {
            String name = field.getName();
            String description = field.getDescription();

            JsonInputBuilder jsonInputBuilder = builders.get(field.getType());

        }

        return null;
    }
}
