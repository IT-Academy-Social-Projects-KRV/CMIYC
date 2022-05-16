package com.ms.data.dto.form;

import com.ms.data.dto.form.input.JsonInput;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class JsonForm {

    private String name;
    private final List<JsonInput> inputs = new ArrayList<>();
}
