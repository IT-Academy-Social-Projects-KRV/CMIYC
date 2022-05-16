package com.ms.data.dto.form;

import com.ms.data.dto.form.input.HtmlInput;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class HtmlForm {

    private String name;
    private final List<HtmlInput> inputs = new ArrayList<>();
    private final List<ApiCombination> combinations = new ArrayList<>();
}
