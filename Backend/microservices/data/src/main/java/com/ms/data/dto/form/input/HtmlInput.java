package com.ms.data.dto.form.input;

import com.ms.data.dto.form.InputType;
import lombok.Getter;

@Getter
public abstract class JsonInput {

    private final String name;
    private final InputType type;
    private final String description;

    protected JsonInput(String name, InputType type, String description) {
        this.name = name;
        this.type = type;
        this.description = description;
    }
}
