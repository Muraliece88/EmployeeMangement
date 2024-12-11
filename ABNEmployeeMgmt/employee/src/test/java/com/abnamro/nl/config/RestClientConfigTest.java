package com.abnamro.nl.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClient;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RestClientConfigTest {
    @InjectMocks
    private RestClientConfig restClientConfig;

    @Test
    void getRestClient() {
        assertNotNull(restClientConfig.getRestClient());
    }
}