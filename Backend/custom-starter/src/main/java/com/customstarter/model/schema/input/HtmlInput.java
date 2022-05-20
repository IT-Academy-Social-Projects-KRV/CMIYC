package com.customstarter.model.schema.input;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;

@Getter
@JsonTypeInfo( use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = TextInput.class, name = "text"),
        @JsonSubTypes.Type(value = NumberInput.class, name = "number"),
        @JsonSubTypes.Type(value = Select.class, name = "select"),
        @JsonSubTypes.Type(value = ComplexInput.class, name = "object")
})
public abstract class HtmlInput {

    private final String name;
    private final HtmlInputType type;
    private final String description;

    protected HtmlInput(String name, HtmlInputType type, String description) {
        this.name = name;
        this.type = type;
        this.description = description;
    }
}
