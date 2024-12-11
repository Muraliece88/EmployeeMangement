package com.abnamro.nl.config;


import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.Duration;

/**
 * Resilience4j configuration
 */
@Configuration
public class ResilienceConfig {

    private final RetryRegistry retryRegistry;

    public ResilienceConfig(RetryRegistry retryRegistry) {
        this.retryRegistry = retryRegistry;
    }

    @Bean
    public Retry registry()
    {
        RetryConfig customConfig = RetryConfig.ofDefaults();
        return retryRegistry.retry("operation");


    }


}
