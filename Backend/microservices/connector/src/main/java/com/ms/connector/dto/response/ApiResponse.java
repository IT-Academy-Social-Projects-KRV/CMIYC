package com.ms.connector.dto.response;

import com.customstarter.model.response.Response;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public abstract class ApiResponse {

    private boolean error;
    private String errorMessage;

    public abstract List<? extends Response> getData();
}
