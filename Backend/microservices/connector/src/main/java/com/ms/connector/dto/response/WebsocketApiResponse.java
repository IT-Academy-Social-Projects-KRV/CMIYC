package com.ms.connector.dto.response;

import com.customstarter.model.response.ResponseThree;
import lombok.Setter;

import java.util.List;

@Setter
public class WebsocketApiResponse extends ApiResponse {

    private List<ResponseThree> data;

    @Override
    public List<ResponseThree> getData() {
        return data;
    }
}
