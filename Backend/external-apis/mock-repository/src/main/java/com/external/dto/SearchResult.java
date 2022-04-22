package com.external.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchResult {

    private final boolean isError;
    private final String errorMessage;
    private final List<PersonData> data;

    public static SearchResult success(List<PersonData> data) {
        return new SearchResult(false, null, data);
    }

    public static SearchResult error(String errorMessage) {
        return new SearchResult(true, errorMessage, null);
    }
}
