package com.ms.data.service.builders.complex;

import com.ms.data.dto.form.input.ComplexInput;
import com.ms.data.dto.form.input.HtmlInput;
import com.ms.data.dto.xml.Field;
import com.ms.data.dto.xml.FieldComponent;
import com.ms.data.service.SearchFormBuilderService;
import com.ms.data.service.builders.HtmlInputBuilderByType;
import org.springframework.stereotype.Component;

@Component
public class NameObjectInputBuilder extends HtmlInputBuilderByType {

    @Override
    public boolean canBuild(String type) {
        return "Name".equals(type);
    }

    @Override
    public HtmlInput build(Field field, SearchFormBuilderService searchFormBuilderService) {
        ComplexInput nameInput = new ComplexInput(
                field.getName(), field.getDescription(), field.getMaxLength()
        );

        for (FieldComponent component : field.getComponents()) {
            Field componentAsField = component.asField(field);

            HtmlInput htmlInput = searchFormBuilderService.buildInput(componentAsField);
            nameInput.getComponents().add(htmlInput);
        }

        return nameInput;
    }
}
