package com.customstarter.model.schema.combination;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CombinationFieldReference {

    private String field;
    private boolean required;
}
