package com.external.dto;

import com.customstarter.model.response.Response;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class SearchResponse {

    private boolean error;
    private String errorMessage;
    private List<Response> data;

    public static SearchResponse success(List<Response> data) {
        return new SearchResponse(false, null, data);
    }

    public static SearchResponse error(String errorMessage) {
        return new SearchResponse(true, errorMessage, null);
    }
}
