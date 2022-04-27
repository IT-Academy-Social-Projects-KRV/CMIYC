package com.ms.connector.service.api.converter;

import com.ms.connector.dto.SearchQuery;
import com.ms.connector.dto.SearchResponse;

public interface BodyConverter {

    String queryToRequestBody(SearchQuery query);

    SearchResponse responseBodyToObject(String response);
}
