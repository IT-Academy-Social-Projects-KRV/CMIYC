package com.ms.data.dto.form;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class ApiCombination {
    private final String apiName;
    private final List<CombinationFieldReference> fields = new ArrayList<>();
}
