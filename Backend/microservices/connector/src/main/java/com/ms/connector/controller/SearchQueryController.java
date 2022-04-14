package com.ms.connector.controller;

import com.ms.connector.model.SearchQuery;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


@RestController
@RequestMapping("/searcher")
public class SearchQueryController {

    @PostMapping
    public Map<String, Map<String, String>> searcher(@RequestBody SearchQuery searchQuery) {

        Map<String, Map<String, String>> response = new HashMap<>();


        if (searchQuery.getForeignDataSource().contains("api1")) {
            Map<String, String> api1Response = new HashMap<>();

            api1Response.put("middlename", "Aristarhovich");
            api1Response.put("email", "test@gmail.com");

            response.put("api1", api1Response);
        }


        if (searchQuery.getForeignDataSource().contains("api2")) {
            Map<String, String> api2Response = new HashMap<>();

            api2Response.put("DriveLisenceNumber", "LBS1339");
            api2Response.put("DriveLisenceValid", "true");
            api2Response.put("DriveLisenceBeginDate", "01/11/2001");

            response.put("api2", api2Response);
        }

        if (searchQuery.getForeignDataSource().contains("api3")) {
            Map<String, String> api3Response = new HashMap<>();

            api3Response.put("car_model", "Opel traffic");
            api3Response.put("car_number", "AA1234BB");
            api3Response.put("car_date_registration", "05/12/2019");

            response.put("api3", api3Response);
        }

        return response;
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
