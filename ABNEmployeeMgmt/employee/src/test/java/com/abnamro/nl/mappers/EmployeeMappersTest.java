package com.abnamro.nl.mappers;

import com.abnamro.nl.dtos.CreateDto;
import com.abnamro.nl.dtos.CreateResponseDto;
import com.abnamro.nl.dtos.EmployeeRequestDto;
import com.abnamro.nl.dtos.EmployeeResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeMappersTest {
    private EmployeeMappers employeeMappers;

    @BeforeEach
    void setUp() {
        employeeMappers = EmployeeMappers.INSTANCE;
    }

    @Test
    void testMapNameRole() {

        EmployeeRequestDto employeeRequestDto = new EmployeeRequestDto("John", "Doe");
        String role = "ADMIN";
        CreateDto createDto = employeeMappers.mapNameRole(employeeRequestDto, role);
        assertEquals("John Doe", createDto.name());
        assertEquals(2L, createDto.role_id());
    }

    @Test
    void testEmployeeFullName() {

        EmployeeRequestDto employeeRequestDto = new EmployeeRequestDto("John", "Doe");
        String fullName = employeeMappers.employeeFullName(employeeRequestDto);
        assertEquals("John Doe", fullName);
    }

    @Test
    void testEmployeeFullNameNoSurname() {
        EmployeeRequestDto employeeRequestDto = new EmployeeRequestDto("John", null);
        String fullName = employeeMappers.employeeFullName(employeeRequestDto);
        assertEquals("John", fullName);
    }

    @Test
    void testMapToResponse() {

        CreateResponseDto createResponseDto = new CreateResponseDto(1L, "John Doe", 1L);
        EmployeeResponseDto employeeResponseDto = employeeMappers.mapToResponse(createResponseDto);
        assertEquals("John", employeeResponseDto.firstName());
        assertEquals("Doe", employeeResponseDto.surname());
    }
    @Test
    void testRoleId() {
        assertEquals(2L, employeeMappers.getRoleId("ADMIN"));
        assertEquals(1L, employeeMappers.getRoleId("MANGER"));
        assertEquals(3L, employeeMappers.getRoleId("USER"));
    }
}

