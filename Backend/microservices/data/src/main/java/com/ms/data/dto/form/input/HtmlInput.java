package com.ms.data.dto.form.input;

import com.ms.data.dto.form.HtmlInputType;
import lombok.Getter;

@Getter
public abstract class HtmlInput {

    private final String name;
    private final HtmlInputType type;
    private final String description;

    protected HtmlInput(String name, HtmlInputType type, String description) {
        this.name = name;
        this.type = type;
        this.description = description;
    }
}
