package com.ms.connector.service.api.converter;

import com.ms.connector.dto.SearchQuery;

public interface BodyConverter {

    String queryToRequestBody(SearchQuery query);

    Object responseBodyToObject(String response);
}
