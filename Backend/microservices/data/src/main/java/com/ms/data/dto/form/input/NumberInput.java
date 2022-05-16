package com.ms.data.dto.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NumberInput extends JsonInput {

    private final Integer min;
    private final Integer max;

    public NumberInput(String name, Integer min, Integer max, String description) {
        super(name, InputType.number, description);

        this.min = min;
        this.max = max;
    }
}
