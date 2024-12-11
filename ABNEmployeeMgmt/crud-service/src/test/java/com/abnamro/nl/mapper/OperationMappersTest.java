package com.abnamro.nl.mapper;

import com.abnamro.nl.constants.RolesEnum;
import com.abnamro.nl.dto.EmployeeRequestDto;
import com.abnamro.nl.dto.EmployeeResponseDto;
import com.abnamro.nl.entities.Employee;
import com.abnamro.nl.entities.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class OperationMappersTest {
    private OperationMappers operationMappers;
    @BeforeEach
    void setUp() {
        operationMappers = Mappers.getMapper(OperationMappers.class);
    }
    @Test
    void testDtoToEmployee() {
        EmployeeRequestDto employeeREquestDto = new EmployeeRequestDto("John Doe", 2L);
        Employee employee = operationMappers.dtoToemployee(employeeREquestDto);
        assertNotNull(employee);
        assertEquals("John", employee.getFirstname());
        assertEquals("Doe", employee.getSurname());
        assertEquals(RolesEnum.ADMIN.name(), employee.getRole().getName());
        assertEquals(2L, employee.getRole().getId());
    }
    @Test
    void testEmployeeToResponse() {
        Employee employee = new Employee();
        employee.setFirstname("John");
        employee.setSurname("Doe");
        Role role=new Role();
        role.setId(2L);
        employee.setRole(role);
        EmployeeResponseDto responseDto = operationMappers.employeeToresponse(employee);
        assertNotNull(responseDto);
        assertEquals("John Doe", responseDto.name());

    }

}