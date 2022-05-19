package com.external.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Response {
    private  String apiName;

    protected Response() {

    }
}
