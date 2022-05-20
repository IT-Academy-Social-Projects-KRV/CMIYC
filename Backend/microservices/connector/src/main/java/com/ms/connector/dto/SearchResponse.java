package com.ms.connector.dto;

import com.customstarter.model.response.ResponseOne;
import com.customstarter.model.response.ResponseThree;
import com.customstarter.model.response.ResponseTwo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class SearchResponse {

    private List<ResponseOne> api1Responses = new ArrayList<>();
    private List<ResponseTwo> api2Responses = new ArrayList<>();
    private List<ResponseThree> api3Responses = new ArrayList<>();
}
