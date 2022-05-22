package com.external.dto;

import com.external.dto.request.Payload;
import com.external.exception.BadRequestException;
import com.external.exception.NoSplitInRequestException;
import com.external.exception.UnknownApiException;
import com.external.utils.MapperUtils;
import lombok.Getter;
import lombok.SneakyThrows;

@Getter
public class SearchRequest {

    public static final String REQUEST_SPLIT = "_";

    private final API api;
    private final Payload requestPayload;

    private SearchRequest(API api, Payload requestPayload) {
        this.api = api;
        this.requestPayload = requestPayload;
    }

    @SneakyThrows
    public static SearchRequest fromMessage(String message) {
        if (!message.contains(REQUEST_SPLIT)) {
            throw new NoSplitInRequestException();
        }

        String apiName = message.substring(0, message.indexOf(REQUEST_SPLIT));
        String json = message.substring(message.indexOf(REQUEST_SPLIT) + REQUEST_SPLIT.length());

        for (API api : API.values()) {
            if (!api.name()
                    .equals(apiName.toUpperCase())) {
                continue;
            }

            try {
                Payload requestPayload = (Payload) MapperUtils.objectMapper.readValue(
                        json,
                        api.getRequestClass()
                );
                return new SearchRequest(api, requestPayload);
            } catch (Exception e) {
                throw new BadRequestException(json);
            }
        }

        throw new UnknownApiException(apiName);
    }

}
