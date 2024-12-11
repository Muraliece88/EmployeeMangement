package com.abnamro.nl.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeDtoTest {

    @Test
    void testEmpDto()
    {
        EmployeeRequestDto employeeREquestDto = new EmployeeRequestDto("John test",1L);
        assertEquals(employeeREquestDto.name(),"John test");
        assertEquals(employeeREquestDto.role_id(),1L);
    }

}