package com.ms.data.dto.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CombinationFieldReference {

    private final String field;
    private final boolean required;
}
