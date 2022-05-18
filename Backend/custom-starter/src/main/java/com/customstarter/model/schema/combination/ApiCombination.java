package com.customstarter.model.schema.combination;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ApiCombination {
    private String apiName;
    private List<CombinationFieldReference> fields = new ArrayList<>();

    public ApiCombination(String apiName) {
        this.apiName = apiName;
    }
}
