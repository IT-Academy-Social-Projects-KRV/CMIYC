package com.ms.search.cache;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Arrays;

@Getter
@Setter
public class TokenCacheKey implements Serializable {

    private final String token;
    private final Object[] params;
    private transient int hashCode;
    private String email;

    public TokenCacheKey(String token, Object[] params) {
        this.token = token;
        this.params = params;
        this.hashCode = Arrays.deepHashCode(this.params);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return this == obj ||
                obj instanceof TokenCacheKey && Arrays.deepEquals(this.params, ((TokenCacheKey) obj).params);
    }

    @Override
    public int hashCode() {
        return this.hashCode;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " [" + StringUtils.arrayToCommaDelimitedString(this.params) + "]";
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        this.hashCode = Arrays.deepHashCode(this.params);
    }
}
