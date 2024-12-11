package com.abnamro.nl.dtos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeRequestDtoTest {

    @Test
    void testRequest() {
        EmployeeRequestDto requestDto=new EmployeeRequestDto("John","Doe");
        assertEquals(requestDto.firstName(),"John");
        assertEquals(requestDto.surName(),"Doe");

    }
}