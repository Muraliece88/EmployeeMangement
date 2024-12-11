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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SecurityConfigTest {
    @InjectMocks
    private SecurityConfig securityConfig;
    @Mock
    private HttpSecurity mockSecurity;

    @Test
    void securityFilterChain() throws Exception {
        Mockito.when(mockSecurity.csrf(ArgumentMatchers.any(Customizer.class))).thenReturn(mockSecurity);
        Mockito.when(mockSecurity.authorizeHttpRequests(ArgumentMatchers.any())).thenReturn(mockSecurity);
        assertNull(securityConfig.securityFilterChain(mockSecurity));
    }

    @Test
    void passwordEncoder() {
        PasswordEncoder result= securityConfig.passwordEncoder();
        assertNotNull(result.encode("test"));
    }

    @Test
    void userDetailsService() {
        UserDetailsService usersMock= securityConfig.userDetailsService();
        assertEquals(usersMock.loadUserByUsername("user").getUsername(),"user");
    }
}