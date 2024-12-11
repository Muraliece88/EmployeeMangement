package com.abnamro.nl.service;

import com.abnamro.nl.constants.RolesEnum;
import com.abnamro.nl.dto.EmployeeRequestDto;
import com.abnamro.nl.dto.EmployeeResponseDto;
import com.abnamro.nl.entities.Employee;
import com.abnamro.nl.entities.Role;
import com.abnamro.nl.exceptions.EmployeeNotFoundException;
import com.abnamro.nl.mapper.OperationMappers;
import com.abnamro.nl.repo.EmployeeRepo;
import com.abnamro.nl.repo.RoleRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


/**
 *
 * Implementaion of business logic
 */
@Service
@Slf4j
public class OperationServiceImpl implements OperationService {
    private final EmployeeRepo employeeRepo;
    private final RoleRepo roleRepo;
    private final OperationMappers operationMappers;

    public OperationServiceImpl(EmployeeRepo employeeRepo, RoleRepo roleRepo, OperationMappers operationMappers) {
        this.employeeRepo = employeeRepo;
        this.roleRepo = roleRepo;
        this.operationMappers = operationMappers;
    }


    /**
     *
     * Method to save newly created employee
     * @param employeeREquestDto
     * @return
     */
    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ResponseEntity<EmployeeResponseDto> saveEmployee(EmployeeRequestDto employeeREquestDto) {
        Role role= roleRepo.findById(employeeREquestDto.role_id()).orElse(null);
        if(role==null)
        {
            role= new Role();
            role.setId(employeeREquestDto.role_id());
            role.setName(RolesEnum.getNameByLabel(employeeREquestDto.role_id()));
            roleRepo.save(role);
        }
        Employee employee= employeeRepo.save(operationMappers.dtoToemployee(employeeREquestDto));
        return new ResponseEntity<>((operationMappers.employeeToresponse(employee)),
                HttpStatusCode.valueOf(HttpStatus.CREATED.value()));

    }

    /**
     * Method to fetch the details of the employee only  that is saved in db
     * @param Id
     * @return
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ResponseEntity<EmployeeResponseDto> fetchEmployee(Long Id) {
        Optional<Employee> optionalEmp=employeeRepo.findById(Id);
        if(optionalEmp.
                isPresent())
        {
            return ResponseEntity.ok(operationMappers.employeeToresponse(optionalEmp.get()));
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Update the details of the employee
     * @param Id
     * @param employeeREquestDto
     * @return
     */
    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ResponseEntity<EmployeeResponseDto> updateEmployee(Long Id, EmployeeRequestDto employeeREquestDto) {
        Employee employee= employeeRepo.findById(Id).
               orElseThrow(()-> new EmployeeNotFoundException("No employee found for the requested Id"));
        roleRepo.findById(employeeREquestDto.role_id()).
                ifPresentOrElse(  value -> log.info("Role exists hence proceeding to update"),
                () -> {
                    log.info("Role doesnt exists hence creating new");
                    Role role= new Role();
                    role.setId(employeeREquestDto.role_id());
                    role.setName(RolesEnum.getNameByLabel(employeeREquestDto.role_id()));
                    roleRepo.save(role);
                });

        Employee updatedEmployee=operationMappers.dtoToemployee(employeeREquestDto);
        updatedEmployee.setId(employee.getId());
        return ResponseEntity.ok(operationMappers.employeeToresponse(employeeRepo.save(updatedEmployee)));

    }

    /**
     * method to delete employee and also to trigger the SP to delete the employee in that role
     * @param Id
     * @return
     */

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ResponseEntity<Map<String, String>> deleteEmployee(Long Id) {
        Employee employee=  employeeRepo.findById(Id).
                orElseThrow(()-> new EmployeeNotFoundException("No employee found for the requested Id"));
        employeeRepo.deleteRoleSp(employee.getRole().getId(), 1L);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Employee Deleted Successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}
