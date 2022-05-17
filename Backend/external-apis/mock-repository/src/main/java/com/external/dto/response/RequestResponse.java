package com.external.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class RequestResponse {
    private  String apiName;

    protected RequestResponse() {

    }
}
