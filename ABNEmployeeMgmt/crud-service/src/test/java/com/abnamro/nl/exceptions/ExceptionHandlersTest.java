package com.abnamro.nl.exceptions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ExceptionHandlersTest {
    @InjectMocks
    private ExceptionHandlers handler;

    @Test
    void handleException() {
        EmployeeNotFoundException techException= Mockito.mock(EmployeeNotFoundException.class);
        ResponseEntity<Errors> result1= handler.handleException(techException);
        assertEquals(result1.getStatusCode().value(),404);

    }}