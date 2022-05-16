package com.ms.data.dto.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TextInput extends JsonInput {

    private final Integer maxLength;

    public TextInput(String name, Integer maxLength, String description) {
        super(name, InputType.text, description);
        this.maxLength = maxLength;
    }
}
