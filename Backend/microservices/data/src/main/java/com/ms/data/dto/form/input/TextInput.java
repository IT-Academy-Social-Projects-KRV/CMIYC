package com.ms.data.dto.form.input;

import com.ms.data.dto.form.HtmlInputType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TextInput extends HtmlInput {

    private Integer maxLength;
    private String regex;

    public TextInput(String name, String description, Integer maxLength, String regex) {
        super(name, HtmlInputType.text, description);

        this.maxLength = maxLength;
        this.regex = regex;
    }
}
