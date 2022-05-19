package com.customstarter.model.schema.input;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Select extends HtmlInput {

    private final List<SelectOption> options = new ArrayList<>();

    public Select(String name, String description) {
        super(name, HtmlInputType.select, description);
    }
}
