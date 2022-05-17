package com.ms.data.dto.form.input;

import com.ms.data.dto.form.HtmlInputType;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ComplexInput extends HtmlInput {

    private final List<HtmlInput> components = new ArrayList<>();
    private final Integer maxLength;

    public ComplexInput(String name, String description, Integer maxLength) {
        super(name, HtmlInputType.object, description);
        this.maxLength = maxLength;
    }
}
