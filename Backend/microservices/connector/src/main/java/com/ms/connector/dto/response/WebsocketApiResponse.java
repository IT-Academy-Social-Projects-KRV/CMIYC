package com.ms.connector.dto.response;

import com.ms.connector.dto.response.data.WebsocketResponseData;
import lombok.Setter;

import java.util.List;

@Setter
public class WebsocketApiResponse extends ApiResponse {

    private List<WebsocketResponseData> data;

    @Override
    public List<WebsocketResponseData> getData() {
        return data;
    }
}
