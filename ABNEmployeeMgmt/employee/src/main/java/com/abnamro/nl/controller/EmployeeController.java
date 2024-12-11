package com.abnamro.nl.controller;
import com.abnamro.nl.constants.RolesEnum;
import com.abnamro.nl.dtos.EmployeeRequestDto;
import com.abnamro.nl.dtos.EmployeeResponseDto;
import com.abnamro.nl.service.EmployeeServiceImpl;
import com.abnamro.nl.validator.EnumValidator;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeServiceImpl serviceImpl;

    public EmployeeController(EmployeeServiceImpl serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    /**
     * Api end point to create an employee
     * @param employeeDto
     * @param role
     * @return
     */

    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EmployeeResponseDto> createEmployee(@RequestBody @Valid EmployeeRequestDto employeeDto,
                                                              @Valid @RequestHeader
                                                              @Size(min=4, max=7, message = "Name must be have at least 3 characters and no more than 7.")
                                                              @EnumValidator(enumClass = RolesEnum.class) String role)
    {

        log.info("Creating employee : {}", employeeDto);
        return serviceImpl.createEmployee(employeeDto, role);

    }

    /**
     * API endpoint to fetch an employee details
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<EmployeeResponseDto> getEmployee(@PathVariable Long id)
    {
        log.info("Getting the details of employee : {}", id);
        return serviceImpl.fetchEmployee(id);

    }

    /**
     *
     * API endpoint to update details of an employee
     * @param id
     * @param employeeDto
     * @param role
     * @return
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<EmployeeResponseDto> updateEmployee(@PathVariable Long id,
                                                              @RequestBody EmployeeRequestDto employeeDto,
                                                              @Valid @RequestHeader
                                                                  @Size(min=4, max=7, message = "Name must be have at least 3 characters and no more than 7.")
                                                                  @EnumValidator(enumClass = RolesEnum.class) String role)
    {
        log.info("Updating employee : {}", employeeDto);
        return serviceImpl.updateEmployee(id,employeeDto,role);

    }

    /**
     *
     * API end point to delete an exmploee
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map> deleteEmployee(@PathVariable Long id)
    {
        log.info("Deleting  employee : {}", id);
        return serviceImpl.deleteEmployee(id);

    }

}
