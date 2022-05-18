package com.ms.data.dto.form.input;

import com.ms.data.dto.form.HtmlInputType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NumberInput extends HtmlInput {

    private Integer min;
    private Integer max;

    public NumberInput(String name, String description, Integer min, Integer max) {
        super(name, HtmlInputType.number, description);

        this.min = min;
        this.max = max;
    }
}
