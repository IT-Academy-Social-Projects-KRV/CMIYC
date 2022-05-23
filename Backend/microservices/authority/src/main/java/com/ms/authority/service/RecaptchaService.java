package com.ms.authority.service;

import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RecaptchaService {

    @Value("${CAPTCHA_SECRET}")
    private String secret;

    @SneakyThrows
    public void checkResponse(String captcha, String ip) {
        String answer = Jsoup.connect(
                String.format(
                        "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s&remoteip=%s",
                        secret,
                        captcha,
                        ip
                )
        ).ignoreContentType(true).get().body().text();
        if (!answer.contains("\"success\": true")) {
            throw new RuntimeException("Error");
        }
    }
}
