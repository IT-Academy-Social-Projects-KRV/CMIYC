package com.ms.authority.config;

import com.ms.authority.utils.FrontendData;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

// Об'єкт що записує додаткову інформацію в jwt токен що може бути корисною на стороні клієнта
@Component
public class CustomTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        // data це json об'єкт що повернеться на фронт
        final Map<String, Object> data = new HashMap<>();

        FrontendData frontendData = (FrontendData) authentication.getUserAuthentication().getDetails();
        // Додаємо в data email та повне ім'я юзера. На етапі аутентифікації ми поклали цей об'єкт сюди в класі AuthenticationProviderImpl
        frontendData.loadToMap(data);

        // Кладемо справжні скоупи на заміну тим що були вказані при реєстрації клієнта в конфігу
        String scopes = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(", "));
        data.put("scope", scopes);

        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(data);
        return accessToken;
    }
}
