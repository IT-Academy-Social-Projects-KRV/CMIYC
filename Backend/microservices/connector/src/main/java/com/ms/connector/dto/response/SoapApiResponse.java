package com.ms.connector.dto.response;

import com.customstarter.model.response.ResponseTwo;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.List;

@Setter
@XmlRootElement(name="SearchResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class SoapApiResponse extends ApiResponse {

    private List<ResponseTwo> data = new ArrayList<>();

    @Override
    public List<ResponseTwo> getData() {
        return data;
    }
}
