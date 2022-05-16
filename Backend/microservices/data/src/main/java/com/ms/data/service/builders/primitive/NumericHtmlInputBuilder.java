package com.ms.data.service.builders;

import com.ms.data.dto.form.input.HtmlInput;
import com.ms.data.dto.form.input.NumberInput;
import com.ms.data.dto.xml.Field;
import com.ms.data.service.SearchFormBuilderService;
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
