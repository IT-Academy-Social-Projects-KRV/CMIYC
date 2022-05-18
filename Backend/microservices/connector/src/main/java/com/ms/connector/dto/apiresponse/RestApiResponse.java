package com.ms.connector.dto.apiresponse;

import java.util.List;

public class RestApiResponse extends ApiResponse {

    private List<RestApiResponseData> data;

    @Override
    public List<RestApiResponseData> getData() {
        return data;
    }
}
