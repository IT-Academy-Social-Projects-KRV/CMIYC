package com.ms.data.service.builders.select;

import com.customstarter.model.RaceCode;
import com.customstarter.model.schema.input.HtmlInput;
import com.customstarter.model.schema.input.Select;
import com.customstarter.model.schema.input.SelectOption;
import com.ms.data.dto.xml.Field;
import com.ms.data.service.SearchFormBuilderService;
import com.ms.data.service.builders.HtmlInputBuilderByNameAndType;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RaceCodeSelectBuilder extends HtmlInputBuilderByNameAndType {

    private static final List<SelectOption> OPTIONS = Arrays.stream(RaceCode.values())
            .map(raceCode -> new SelectOption(raceCode.name(), raceCode.getRace()))
            .collect(Collectors.toList());

    @Override
    protected boolean canBuild(String name, String type) {
        return "RaceCode".equals(name);
    }

    @Override
    public HtmlInput build(Field field, SearchFormBuilderService searchFormBuilderService) {
        Select select = new Select(field.getName(), field.getDescription());
        select.getOptions().add(SelectOption.SELECT);
        select.getOptions().addAll(OPTIONS);

        return select;
    }
}
