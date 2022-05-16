package com.ms.data.service.builders.select;

import com.ms.data.dto.form.input.HtmlInput;
import com.ms.data.dto.form.input.Select;
import com.ms.data.dto.form.input.SelectOption;
import com.ms.data.dto.xml.Field;
import com.ms.data.service.SearchFormBuilderService;
import com.ms.data.service.builders.HtmlInputBuilderByNameAndType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SexCodeHtmlInputBuilder extends HtmlInputBuilderByNameAndType {

    private static final List<SelectOption> OPTIONS = List.of(
            new SelectOption("Unknown", ""),
            new SelectOption("Male", "M"),
            new SelectOption("Female", "F")
    );

    @Override
    protected boolean canBuild(String name, String type) {
        return "SexCode".equals(name);
    }

    @Override
    public HtmlInput build(Field field, SearchFormBuilderService searchFormBuilderService) {
        Select select = new Select(field.getName(), field.getDescription());
        select.getOptions().addAll(OPTIONS);

        return select;
    }
}
