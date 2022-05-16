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
public class RaceCodeSelectBuilder extends HtmlInputBuilderByNameAndType {

    private static final List<SelectOption> OPTIONS = List.of(
            new SelectOption("", "Unknown"),
            new SelectOption("W", "White"),
            new SelectOption("B", "Black or African American"),
            new SelectOption("A", "Asian"),
            new SelectOption("L", "Latino or Hispanic"),
            new SelectOption("I", "American Indian or Alaska Native"),
            new SelectOption("H", "Native Hawaiian and Other Pacific Islander"),
            new SelectOption("O", "Other")
    );

    @Override
    protected boolean canBuild(String name, String type) {
        return "RaceCode".equals(name);
    }

    @Override
    public HtmlInput build(Field field, SearchFormBuilderService searchFormBuilderService) {
        Select select = new Select(field.getName(), field.getDescription());
        select.getOptions().addAll(OPTIONS);

        return select;
    }
}
