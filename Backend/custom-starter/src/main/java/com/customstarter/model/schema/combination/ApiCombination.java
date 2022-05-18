package com.customstarter.model.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApiCombination {
    private String apiName;
    private List<CombinationFieldReference> fields = new ArrayList<>();
}
