package com.customstarter.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
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
    private LocalDateTime dateTime = LocalDateTime.now();

}
