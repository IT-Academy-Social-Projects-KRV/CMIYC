package com.ms.data.service.builders;

import com.ms.data.dto.form.input.HtmlInput;
import com.ms.data.dto.form.input.TextInput;
import com.ms.data.dto.xml.Field;
import com.ms.data.service.SearchFormBuilderService;
import org.springframework.stereotype.Component;

@Component
public class AlphabeticHtmlInputBuilder extends HtmlInputBuilderByType {

    @Override
    public boolean canBuild(String type) {
        return "Alphabetic".equals(type);
    }

    @Override
    public HtmlInput build(Field field, SearchFormBuilderService searchFormBuilderService) {
        return new TextInput(
                field.getName(), field.getDescription(),
                field.getMaxLength(), "^[A-Za-z]*$"
        );
    }
}
