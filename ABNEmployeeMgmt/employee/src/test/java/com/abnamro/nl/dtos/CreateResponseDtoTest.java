package com.abnamro.nl.dtos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreateResponseDtoTest {
    @Test
    void test() {

        CreateResponseDto responseDto = new CreateResponseDto(1l, "John Doe", 2L);
        assertEquals("John Doe", responseDto.name());
        assertEquals(1L, responseDto.id());
        assertEquals(2L, responseDto.role_id());
    }


}