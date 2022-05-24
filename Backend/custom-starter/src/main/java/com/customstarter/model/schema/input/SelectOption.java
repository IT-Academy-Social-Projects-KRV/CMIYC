package com.customstarter.model.schema.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SelectOption {

    public static final SelectOption SELECT = new SelectOption("", "Select...");

    private String value;
    private String text;
}
