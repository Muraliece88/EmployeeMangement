package com.abnamro.nl.dtos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreateDtoTest {
    @Test
    void testCreateDto() {
        CreateDto createDto = new CreateDto("John Doe", 2L);
        assertEquals("John Doe", createDto.name());
        assertEquals(2L, createDto.role_id());
    }

}