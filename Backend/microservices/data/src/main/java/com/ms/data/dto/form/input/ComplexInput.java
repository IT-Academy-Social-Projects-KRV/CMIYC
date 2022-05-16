package com.ms.data.dto.form.input;

import com.ms.data.dto.form.InputType;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ComplexJsonInput extends JsonInput {

    private final List<JsonInput> components = new ArrayList<>();
    private final Integer maxLength;

    public ComplexJsonInput(String name, String description, Integer maxLength) {
        super(name, InputType.object, description);
        this.maxLength = maxLength;
    }
}
