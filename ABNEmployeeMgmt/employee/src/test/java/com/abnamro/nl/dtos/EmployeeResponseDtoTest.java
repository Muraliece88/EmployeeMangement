package com.abnamro.nl.dtos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeResponseDtoTest {

    @Test
    void testResponse() {
        EmployeeResponseDto responseDto=new EmployeeResponseDto(1L,"John","Doe",1L);
        assertEquals("John", responseDto.firstName());
        assertEquals("Doe", responseDto.surname());
        assertEquals(1L, responseDto.id());
        assertEquals(1L, responseDto.role_id());

    }
}