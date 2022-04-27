package com.ms.connector.controller;

import com.ms.connector.dto.SearchQuery;

import com.ms.connector.dto.SearchResponse;
import com.ms.connector.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/searcher")
public class SearchQueryController {

    @Autowired
    private ApiService apiService;

    @PostMapping
    public Map<String, SearchResponse> searcher(@RequestBody SearchQuery searchQuery) {
        return apiService.handleSearchRequest(searchQuery);
    }

    //for test only.  Get test search query JSON response
    @GetMapping
    public Map<String, Map<String, String>> testQuery() {
        Map<String, Map<String, String>> response = new HashMap<>();

        Map<String, String> api1Response = new HashMap<>();
        api1Response.put("middlename", "Aristarhovich");
        api1Response.put("email", "test@gmail.com");

        Map<String, String> api2Response = new HashMap<>();
        api2Response.put("DriveLisenceNumber", "LBS1339");
        api2Response.put("DriveLisenceValid", "true");
        api2Response.put("DriveLisenceBeginDate", "01/11/2001");

        Map<String, String> api3Response = new HashMap<>();
        api3Response.put("car_model", "Opel traffic");
        api3Response.put("car_number", "AA1234BB");
        api3Response.put("car_date_registration", "05/12/2019");

        response.put("api1", api1Response);
        response.put("api2", api2Response);
        response.put("api3", api3Response);

        return response;
    }

}
