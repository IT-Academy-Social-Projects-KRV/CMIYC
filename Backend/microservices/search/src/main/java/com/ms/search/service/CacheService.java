package com.ms.search.service;

import com.customstarter.model.response.SearchResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@EnableScheduling
public class CacheService {

    @Value("${cache.expiration-time}")
    private int cacheExpirationTimeInHours;
    private final Map<String, List<SearchResponse>> cache = new HashMap<>();

    public List<SearchResponse> findAll(JwtAuthenticationToken jwtAuthenticationToken){
        String email = getEmail(jwtAuthenticationToken);
        return cache.get(email);
    }

    public void save (JwtAuthenticationToken jwtAuthenticationToken, SearchResponse searchResponse){
        String email = getEmail(jwtAuthenticationToken);
        List<SearchResponse> list = cache.getOrDefault(email, new ArrayList<>());
        if (list.isEmpty()){
            cache.put(email, list);
        }
        list.add(0, searchResponse);
    }
    @Scheduled(cron = "0 0/20 * * * *")
    public void deleteOldData(){
        LocalDateTime currentTime = LocalDateTime.now();
        cache.forEach((email, searchResponses) -> {
            searchResponses.removeIf(searchResponse -> {
                long hours = ChronoUnit.HOURS.between(searchResponse.getDateTime(), currentTime);
                return hours >= cacheExpirationTimeInHours;
            });
        });
    }

    private String getEmail(JwtAuthenticationToken jwtAuthenticationToken){

        return (String) jwtAuthenticationToken.getTokenAttributes().get("user_name");
    }
}
