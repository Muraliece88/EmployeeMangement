package com.abnamro.nl.controller;

import com.abnamro.nl.dto.EmployeeRequestDto;
import com.abnamro.nl.dto.EmployeeResponseDto;
import com.abnamro.nl.service.OperationServiceImpl;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RequestMapping("/api/employees")
@RestController

public class OperationsController {
    private  final OperationServiceImpl service;

    public OperationsController(OperationServiceImpl service) {
        this.service = service;
    }

    /**
     * Api end point to create an  employee
     * @param employeeREquestDto
     * @return
     */

    @PostMapping("/")
    public ResponseEntity<EmployeeResponseDto> createEmployee(@RequestBody @Valid EmployeeRequestDto employeeREquestDto)
    {
        log.info("Creating employee from crud-service : {}", employeeREquestDto);
        return service.saveEmployee(employeeREquestDto);

    }

    /**
     * Api end point to fetch details of an employee
     * @param Id
     * @return
     */
    @GetMapping("/{Id}")
    public ResponseEntity<EmployeeResponseDto> getEmployeeDetails(@PathVariable Long Id)
    {
        log.info("Get employee details for the id  {}" +Id);
        return service.fetchEmployee(Id);
    }

    /**
     * Api end point to update details of an employee
     * @param Id
     * @return
     */
    @PutMapping("/{Id}")
    public ResponseEntity<EmployeeResponseDto> updateEmployeeDetails(@RequestBody @Valid EmployeeRequestDto employeeREquestDto, @PathVariable Long Id)
    {
        log.info("Delete employee details for the id  {}" +Id);
        return service.updateEmployee(Id, employeeREquestDto);
    }


    /**
     * Api end point to delete an employee
     * @param Id
     * @return
     */


    @DeleteMapping("/{Id}")
    public ResponseEntity<Map<String, String>> deleteEmployeeDetails(@PathVariable Long Id)
    {
        log.info("Update employee details for the id  {}" +Id);
        return service.deleteEmployee(Id);
    }
}
