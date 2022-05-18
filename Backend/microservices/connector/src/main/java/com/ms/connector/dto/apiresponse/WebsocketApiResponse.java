package com.ms.connector.dto.apiresponse;

import java.util.List;

public class WebsocketApiResponse extends ApiResponse {

    private List<WebsocketResponseData> data;

    @Override
    public List<WebsocketResponseData> getData() {
        return data;
    }
}
