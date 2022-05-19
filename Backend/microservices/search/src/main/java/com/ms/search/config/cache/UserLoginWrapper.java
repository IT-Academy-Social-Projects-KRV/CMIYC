package com.ms.search.config.cache;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserLoginWrapper {
    @JsonProperty("user_name")
    private String userName;
}
