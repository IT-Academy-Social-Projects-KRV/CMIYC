package com.customstarter.model.form.input;

import com.customstarter.model.form.HtmlInputType;
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
