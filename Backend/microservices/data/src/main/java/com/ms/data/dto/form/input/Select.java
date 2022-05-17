package com.ms.data.dto.form.input;

import com.ms.data.dto.form.HtmlInputType;
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
