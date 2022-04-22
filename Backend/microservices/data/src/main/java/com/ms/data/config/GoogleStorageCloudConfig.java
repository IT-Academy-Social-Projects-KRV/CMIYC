package com.ms.data.config;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.storage.Storage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Configuration
public class GoogleStorageCloudConfig {

    @Bean
    Storage configStorageClient() throws GeneralSecurityException, IOException {

        Storage storage = new Storage(GoogleNetHttpTransport.newTrustedTransport(),
                new GsonFactory(),
                new GoogleHttpRequestInitializer()
        );

        return storage;
    }

}
