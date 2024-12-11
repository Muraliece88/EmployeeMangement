package com.abnamro.nl.controller;

import com.abnamro.nl.dtos.CreateDto;
import com.abnamro.nl.dtos.CreateResponseDto;
import com.abnamro.nl.dtos.EmployeeRequestDto;
import com.abnamro.nl.dtos.EmployeeResponseDto;
import com.abnamro.nl.mappers.EmployeeMappers;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestClient;
import org.springframework.web.context.WebApplicationContext;

import java.util.function.Consumer;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class EmployeeControllerTest {
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;
    @MockBean
    private RestClient restClient;
    @Mock
    private RestClient.RequestBodyUriSpec bodyUriSpec;
    @Mock
    private RestClient.RequestHeadersUriSpec headersUriSpec;

    @Mock
    private RestClient.RequestBodySpec mockRequestBodySpec;
    @Mock
    private RestClient.ResponseSpec responseSpec;
    @Mock
    private EmployeeMappers mappers;
    @Mock
    private CreateDto createDto;
    @Mock
    private CreateResponseDto createResponseDto;
    @Mock
    private EmployeeRequestDto employeeDto;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
    }

    @org.junit.jupiter.api.Test
    @WithMockUser(username = "dummy", password = "password", roles = "ADMIN")
    void createEmployee() throws Exception {
        when(restClient.post()).thenReturn(bodyUriSpec);
        when(bodyUriSpec.headers(any(Consumer.class))).thenReturn(mockRequestBodySpec);
        when(mockRequestBodySpec.body(any(Object.class))).thenReturn(mockRequestBodySpec);
        when(mockRequestBodySpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.onStatus(any(), any())).thenReturn(responseSpec);
        when(responseSpec.toEntity(CreateResponseDto.class))
                .thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(createResponseDto));
        when(createResponseDto.name()).thenReturn("John");
        when(mappers.mapNameRole(employeeDto,"ADMIN")).thenReturn(createDto);

        HttpHeaders headers=new HttpHeaders();
        headers.set("Role","ADMIN");
        mockMvc.perform(post("/employees/")
                        .content(" {\n" +
                                "    \"first_name\": \"John\",\n" +
                                "    \"surname\": \"Joe\"  \n" +
                                " }")
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201));
    }

    @org.junit.jupiter.api.Test
    @WithMockUser(username = "dummy", password = "password", roles = "USER")
    void getEmployee() throws Exception {
        when(restClient.get()).thenReturn(headersUriSpec);
        when(headersUriSpec.uri(any(Function.class))).thenReturn(headersUriSpec);
        when(headersUriSpec.headers(any())).thenReturn(bodyUriSpec);
        when(bodyUriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.onStatus(any(), any())).thenReturn(responseSpec);
        when(responseSpec.toEntity(CreateResponseDto.class))
                .thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(createResponseDto));
        when(createResponseDto.name()).thenReturn("John");
        when(mappers.mapNameRole(employeeDto,"USER")).thenReturn(createDto);
        mockMvc.perform(get("/employees/{Id}",1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
           .andExpect(jsonPath("$.id").exists());
    }

    @org.junit.jupiter.api.Test
    @WithMockUser(username = "dummy", password = "password", roles = "USER")
    void updateEmployee() throws Exception {
        when(restClient.put()).thenReturn(bodyUriSpec);
        when(bodyUriSpec.uri(any(Function.class))).thenReturn(mockRequestBodySpec);
        when(mockRequestBodySpec.headers(any(Consumer.class))).thenReturn(mockRequestBodySpec);
        when(mockRequestBodySpec.body(any(Object.class))).thenReturn(mockRequestBodySpec);
        when(mockRequestBodySpec.retrieve()).thenReturn(responseSpec);
        when(bodyUriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.onStatus(any(), any())).thenReturn(responseSpec);
        when(responseSpec.toEntity(CreateResponseDto.class))
                .thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(createResponseDto));
        when(createResponseDto.name()).thenReturn("John");
        when(mappers.mapNameRole(employeeDto,"USER")).thenReturn(createDto);
        HttpHeaders headers=new HttpHeaders();
        headers.set("Role","USER");
        mockMvc.perform(put("/employees/{Id}",1L)
                        .content(" {\n" +
                                "    \"first_name\": \"John\",\n" +
                                "    \"surname\": \"Joe\"  \n" +
                                " }")
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id").exists());
    }

    @org.junit.jupiter.api.Test
    void testDeleteEmployee() {
    }
}