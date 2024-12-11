package com.abnamro.nl.service;

import com.abnamro.nl.dtos.CreateDto;
import com.abnamro.nl.dtos.CreateResponseDto;
import com.abnamro.nl.dtos.EmployeeRequestDto;
import com.abnamro.nl.dtos.EmployeeResponseDto;
import com.abnamro.nl.mappers.EmployeeMappers;
import io.github.resilience4j.core.EventConsumer;
import io.github.resilience4j.retry.Retry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import java.util.Map;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {
    @InjectMocks
    private EmployeeServiceImpl serviceImpl;
    @Mock
    private EmployeeRequestDto employeeDto;
    @Mock
    private RestClient restClient;
    @Mock
    private RestClient.RequestBodyUriSpec bodyUriSpec;
    @Mock
    private RestClient.RequestHeadersUriSpec headersUriSpec;
    @Mock
    private RestClient.ResponseSpec responseSpec;
    @Mock
    private EmployeeMappers mappers;
    @Mock
    private CreateDto createDto;
    @Mock
    private CreateResponseDto createResponseDto;
    @Mock
    private Retry retry;

    @Mock
    private Retry.EventPublisher publisher;

    @Test
    void saveEmployee() {

        when(restClient.post()).thenReturn(bodyUriSpec);
        when(bodyUriSpec.headers(any())).thenReturn(bodyUriSpec);
        when(bodyUriSpec.body(createDto)).thenReturn(bodyUriSpec);
        Mockito.when(bodyUriSpec.headers(any())).thenReturn(bodyUriSpec);
        when(bodyUriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.onStatus(any(), any())).thenReturn(responseSpec);
        when(responseSpec.toEntity(CreateResponseDto.class))
                .thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(createResponseDto));
        when(mappers.mapNameRole(employeeDto,"ROLE")).thenReturn(createDto);
        when(retry.getEventPublisher()).thenReturn(publisher);
        when(publisher.onRetry(any(EventConsumer.class))).thenReturn(publisher);
        doAnswer(invocation -> {
            Runnable runnable = invocation.getArgument(0);
            runnable.run();
            return null;
        }).when(retry).executeRunnable(any(Runnable.class));
        ResponseEntity<EmployeeResponseDto> result=serviceImpl.createEmployee(employeeDto,"ROLE");
        assertEquals(result.getStatusCode().value(),201);
    }

    @Test
    void fetchEmployee() {
        when(restClient.get()).thenReturn(headersUriSpec);
        when(headersUriSpec.uri(any(Function.class))).thenReturn(headersUriSpec);
        when(headersUriSpec.headers(any())).thenReturn(bodyUriSpec);
        when(bodyUriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.onStatus(any(), any())).thenReturn(responseSpec);
        when(retry.getEventPublisher()).thenReturn(publisher);
        when(publisher.onRetry(any(EventConsumer.class))).thenReturn(publisher);
        doAnswer(invocation -> {
            Runnable runnable = invocation.getArgument(0);
            runnable.run();
            return null;
        }).when(retry).executeRunnable(any(Runnable.class));
        when(responseSpec.toEntity(CreateResponseDto.class))
                .thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(createResponseDto));
        ResponseEntity<EmployeeResponseDto> result=serviceImpl.fetchEmployee(1L);
        assertEquals(result.getStatusCode().value(),200);

    }

    @Test
    void updateEmployee() {
        when(restClient.put()).thenReturn(bodyUriSpec);
        when(bodyUriSpec.headers(any())).thenReturn(bodyUriSpec);
        when(bodyUriSpec.body(createDto)).thenReturn(bodyUriSpec);
        when(bodyUriSpec.uri(any(Function.class))).thenReturn(bodyUriSpec);
        Mockito.when(bodyUriSpec.headers(any())).thenReturn(bodyUriSpec);
        when(bodyUriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.onStatus(any(), any())).thenReturn(responseSpec);
        when(retry.getEventPublisher()).thenReturn(publisher);
        when(publisher.onRetry(any(EventConsumer.class))).thenReturn(publisher);
        doAnswer(invocation -> {
            Runnable runnable = invocation.getArgument(0);
            runnable.run();
            return null;
        }).when(retry).executeRunnable(any(Runnable.class));
        when(responseSpec.toEntity(CreateResponseDto.class))
                .thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(createResponseDto));
        when(mappers.mapNameRole(employeeDto,"ROLE")).thenReturn(createDto);
        ResponseEntity<EmployeeResponseDto> result=serviceImpl.updateEmployee(1l,employeeDto,"ROLE");
        assertEquals(result.getStatusCode().value(),200);
    }

    @Test
    void deleteEmployee() {
        Map<String,String> mockResponse=mock(Map.class);
        when(restClient.delete()).thenReturn(headersUriSpec);
        when(headersUriSpec.uri(any(Function.class))).thenReturn(headersUriSpec);
        when(headersUriSpec.headers(any())).thenReturn(bodyUriSpec);
        when(bodyUriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.onStatus(any(), any())).thenReturn(responseSpec);
        when(retry.getEventPublisher()).thenReturn(publisher);
        when(publisher.onRetry(any(EventConsumer.class))).thenReturn(publisher);
        doAnswer(invocation -> {
            Runnable runnable = invocation.getArgument(0);
            runnable.run();
            return null;
        }).when(retry).executeRunnable(any(Runnable.class));
        when(responseSpec.toEntity(Map.class))
                .thenReturn(ResponseEntity.status(HttpStatus.OK).body(mockResponse));
        ResponseEntity<Map> result=serviceImpl.deleteEmployee(1l);
        assertEquals(result.getStatusCode().value(),200);
    }


}