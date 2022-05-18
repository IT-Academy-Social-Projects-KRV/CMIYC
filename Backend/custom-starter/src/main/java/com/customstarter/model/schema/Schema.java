package com.customstarter.model.schema;

import com.customstarter.model.schema.combination.ApiCombination;
import com.customstarter.model.schema.input.HtmlInput;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class Schema {

    private String name;
    private final List<HtmlInput> inputs = new ArrayList<>();
    private final List<ApiCombination> combinations = new ArrayList<>();
}
