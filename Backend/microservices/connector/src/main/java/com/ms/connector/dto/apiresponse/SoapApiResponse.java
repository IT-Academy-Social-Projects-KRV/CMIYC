package com.ms.connector.dto.apiresponse;

import java.util.List;

public class SoapApiResponse extends ApiResponse {

    private List<SoapApiResponseData> data;

    @Override
    public List<SoapApiResponseData> getData() {
        return data;
    }
}
