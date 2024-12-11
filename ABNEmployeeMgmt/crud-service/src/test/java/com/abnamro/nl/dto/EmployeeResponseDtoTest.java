package com.abnamro.nl.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class EmployeeResponseDtoTest {
    @Test
    void empResponse()
    {
        EmployeeResponseDto responseDto=new EmployeeResponseDto(1L, "Test Test", 1L);
        assertEquals(responseDto.id(),1L);
        assertEquals(responseDto.name(),"Test Test");
        assertEquals(responseDto.role_id(), 1L);
    }

}