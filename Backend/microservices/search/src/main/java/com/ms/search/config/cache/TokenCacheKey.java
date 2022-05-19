package com.ms.search.config.cache;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
public class TokenCacheKey implements Serializable {

    private final String token;
    private final SearchRequestWrapper timeSearchRequest;
    private transient int hashCode;
    private String email;

    public TokenCacheKey(String token, SearchRequestWrapper timeSearchRequest) {
        this.token = token;
        this.timeSearchRequest = timeSearchRequest;
        Object[] obj = new Object[2];
        obj[0] = this.token;
        obj[1] = this.timeSearchRequest.getSearchRequest();
        this.hashCode = Arrays.deepHashCode(obj);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return this == obj ||
                obj instanceof TokenCacheKey && Objects.equals(this.token, ((TokenCacheKey) obj).getToken())
                        && Objects.equals(this.timeSearchRequest.getSearchRequest(), ((TokenCacheKey) obj).timeSearchRequest.getSearchRequest());
    }

    @Override
    public int hashCode() {
        return this.hashCode;
    }
}
