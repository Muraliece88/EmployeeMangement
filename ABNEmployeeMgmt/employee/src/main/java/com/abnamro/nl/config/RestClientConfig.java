package com.abnamro.nl.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {
    @Value("${operations.api.url}")
    private String apiurl;

    @Bean
    public RestClient getRestClient() {

        return RestClient.builder()
                .baseUrl(apiurl)
                .build();
    }
}
