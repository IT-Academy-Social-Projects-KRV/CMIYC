package com.ms.data.service.builders.primitive;

import com.customstarter.model.schema.input.HtmlInput;
import com.customstarter.model.schema.input.NumberInput;
import com.ms.data.dto.xml.Field;
import com.ms.data.service.SearchFormBuilderService;
import com.ms.data.service.builders.HtmlInputBuilderByType;
import org.springframework.stereotype.Component;

@Component
public class NumericHtmlInputBuilder extends HtmlInputBuilderByType {

    @Override
    public boolean canBuild(String type) {
        return "Numeric".equals(type);
    }

    @Override
    public HtmlInput build(Field field, SearchFormBuilderService searchFormBuilderService) {
        return new NumberInput(
                field.getName(), field.getDescription(),
                null, getMaxFromMaxLength(field.getMaxLength())
        );
    };

    public static Integer getMaxFromMaxLength(Integer maxLength) {
        return maxLength == null ?
                null :
                Integer.parseInt("9".repeat(maxLength));
    }
}
