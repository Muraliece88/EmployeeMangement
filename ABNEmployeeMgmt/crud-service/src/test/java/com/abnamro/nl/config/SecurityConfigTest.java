package com.abnamro.nl.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SecurityConfigTest {
    @InjectMocks
    private SecurityConfig securityConfig;
    @Mock
    private HttpSecurity mockSecurity;
    @Mock
    private HandlerMappingIntrospector introspector;

    @Test
    void securityFilterChain() throws Exception {
        Mockito.when(mockSecurity.csrf(ArgumentMatchers.any(Customizer.class))).thenReturn(mockSecurity);
        Mockito.when(mockSecurity.authorizeHttpRequests(ArgumentMatchers.any())).thenReturn(mockSecurity);
        assertNull(securityConfig.securityFilterChain(mockSecurity,introspector));
    }


}