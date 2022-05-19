package com.customstarter.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Response {

    private  String apiName;

    protected Response() {

    }
}
