package com.abnamro.nl.service;

import com.abnamro.nl.dto.EmployeeRequestDto;
import com.abnamro.nl.dto.EmployeeResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.Map;


/**
 *
 * Interface to allow multiple definition which can be later implemented
 */
public interface OperationService {
    ResponseEntity<EmployeeResponseDto> saveEmployee(EmployeeRequestDto employeeREquestDto);
    ResponseEntity<EmployeeResponseDto> fetchEmployee(Long Id);
    ResponseEntity<EmployeeResponseDto>  updateEmployee(Long Id, EmployeeRequestDto employeeREquestDto);
    ResponseEntity<Map<String, String>>  deleteEmployee(Long Id);
}
