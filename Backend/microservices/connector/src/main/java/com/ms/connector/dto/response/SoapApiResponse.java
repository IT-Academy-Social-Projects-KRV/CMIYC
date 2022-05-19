package com.ms.connector.dto.response;

import com.customstarter.model.response.ResponseTwo;
import lombok.Setter;

import java.util.List;

@Setter
public class SoapApiResponse extends ApiResponse {

    private List<ResponseTwo> data;

    @Override
    public List<ResponseTwo> getData() {
        return data;
    }
}
