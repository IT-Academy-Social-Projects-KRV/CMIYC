package com.ms.connector.dto.response;

import com.ms.connector.dto.response.data.SoapApiResponseData;
import lombok.Setter;

import java.util.List;

@Setter
public class SoapApiResponse extends ApiResponse {

    private List<SoapApiResponseData> data;

    @Override
    public List<SoapApiResponseData> getData() {
        return data;
    }
}
