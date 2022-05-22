package com.customstarter.model.response;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlTransient;

@Getter
@Setter
public abstract class Response {

    private  String apiName;

    protected Response() {

    }
}
