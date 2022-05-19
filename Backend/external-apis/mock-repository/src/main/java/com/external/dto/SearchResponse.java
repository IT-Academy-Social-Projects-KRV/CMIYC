package com.external.dto;

import com.external.dto.response.Response;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchResponse {

    private final boolean isError;
    private final String errorMessage;
    private final List<Response> data;

    public static SearchResponse success(List<Response> data) {
        return new SearchResponse(false, null, data);
    }

    public static SearchResponse error(String errorMessage) {
        return new SearchResponse(true, errorMessage, null);
    }
}
