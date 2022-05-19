package com.ms.data.service.builders.primitive;

import com.customstarter.model.schema.input.HtmlInput;
import com.customstarter.model.schema.input.TextInput;
import com.ms.data.dto.xml.Field;
import com.ms.data.service.SearchFormBuilderService;
import com.ms.data.service.builders.HtmlInputBuilderByType;
import org.springframework.stereotype.Component;

@Component
public class AlphanumericHtmlInputBuilder extends HtmlInputBuilderByType {

    @Override
    public boolean canBuild(String type) {
        return "Alphanumeric".equals(type);
    }

    @Override
    public HtmlInput build(Field field, SearchFormBuilderService searchFormBuilderService) {
        return new TextInput(
                field.getName(), field.getDescription(),
                field.getMaxLength(), "^[0-9A-Za-z]*$"
        );
    }
}
