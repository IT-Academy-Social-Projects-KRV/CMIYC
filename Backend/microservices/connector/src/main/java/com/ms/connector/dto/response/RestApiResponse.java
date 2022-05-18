package com.ms.connector.dto.response;

import com.ms.connector.dto.response.data.RestApiResponseData;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
public class RestApiResponse extends ApiResponse {

    private List<RestApiResponseData> data;

    @Override
    public List<RestApiResponseData> getData() {
        return data;
    }
}
