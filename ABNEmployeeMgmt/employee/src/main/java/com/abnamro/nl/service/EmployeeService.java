package com.abnamro.nl.service;

import com.abnamro.nl.dtos.EmployeeRequestDto;
import com.abnamro.nl.dtos.EmployeeResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface EmployeeService {

    ResponseEntity<EmployeeResponseDto> createEmployee(EmployeeRequestDto employeeDto, String role);
    ResponseEntity<EmployeeResponseDto> fetchEmployee(Long Id);
    ResponseEntity<EmployeeResponseDto> updateEmployee(Long Id,EmployeeRequestDto employeeDto, String role);
    ResponseEntity<Map> deleteEmployee(Long Id);
}
