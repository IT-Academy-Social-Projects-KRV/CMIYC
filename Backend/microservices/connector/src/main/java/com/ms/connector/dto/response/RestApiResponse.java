package com.ms.connector.dto.response;

import com.customstarter.model.response.ResponseOne;
import lombok.Setter;

import java.util.List;

@Setter
public class RestApiResponse extends ApiResponse {

    private List<ResponseOne> data;

    @Override
    public List<ResponseOne> getData() {
        return data;
    }
}
