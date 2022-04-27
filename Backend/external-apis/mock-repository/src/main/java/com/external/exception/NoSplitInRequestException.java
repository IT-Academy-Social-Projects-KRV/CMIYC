package com.external.exception;

import com.external.dto.SearchRequest;

public class NoSplitInRequestException extends RuntimeException {

    private static final String EXCEPTION_MESSAGE = String.format(
            "Split string %s was not included in request",
            SearchRequest.REQUEST_SPLIT
    );

    public NoSplitInRequestException() {
        super(EXCEPTION_MESSAGE);
    }

}
