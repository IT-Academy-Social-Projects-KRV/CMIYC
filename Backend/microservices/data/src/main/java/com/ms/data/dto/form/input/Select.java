package com.ms.data.dto.form;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Select extends JsonInput {

    private final List<SelectOption> options = new ArrayList<>();

    protected Select(String name, String description) {
        super(name, InputType.select, description);
    }
}
