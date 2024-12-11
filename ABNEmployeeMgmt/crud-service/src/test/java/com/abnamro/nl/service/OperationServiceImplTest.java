package com.abnamro.nl.service;

import com.abnamro.nl.dto.EmployeeRequestDto;
import com.abnamro.nl.dto.EmployeeResponseDto;
import com.abnamro.nl.entities.Employee;
import com.abnamro.nl.entities.Role;
import com.abnamro.nl.mapper.OperationMappers;
import com.abnamro.nl.repo.EmployeeRepo;
import com.abnamro.nl.repo.RoleRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OperationServiceImplTest {
    @InjectMocks
    private OperationServiceImpl service;
    @Mock
    private EmployeeRequestDto employeeREquestDto;
    @Mock
    private RoleRepo roleRepo;
    @Mock
    private EmployeeRepo employeeRepo;
    @Mock
    private OperationMappers operationMappers;

    @Test
    void saveEmployee() {
        Optional<Role> mockOpt=Mockito.mock(Optional.class);
        Mockito.when(employeeREquestDto.role_id()).thenReturn(1L);
        Mockito.when(roleRepo.findById(ArgumentMatchers.anyLong())).thenReturn(mockOpt);
        ResponseEntity<EmployeeResponseDto> result= service.saveEmployee(employeeREquestDto);
        assertEquals(result.getStatusCode().value(),201);
    }

    @Test
    void fetchEmployeePresent() {
        Optional<Employee> mockOpt=Mockito.mock(Optional.class);
        Mockito.when(employeeRepo.findById(ArgumentMatchers.anyLong())).thenReturn(mockOpt);
        Mockito.when(mockOpt.isPresent()).thenReturn(true);
        assertEquals(service.fetchEmployee(1L).getStatusCode().value(),200);
    }
    @Test
    void fetchEmployeeNotFound() {
        Optional<Employee> mockOpt=Mockito.mock(Optional.class);
        Mockito.when(employeeRepo.findById(ArgumentMatchers.anyLong())).thenReturn(mockOpt);
        Mockito.when(mockOpt.isPresent()).thenReturn(false);
        assertEquals(service.fetchEmployee(1L).getStatusCode().value(),404);
    }

   @Test
    void updateEmployee() {
        EmployeeRequestDto mockDto=new EmployeeRequestDto("test name", 1L);
        Employee mockEmp=new Employee();
        Role mockRole1=new Role();
        mockRole1.setId(1L);
        mockRole1.setName("ADMIN");
        mockEmp.setId(1L);
        mockEmp.setFirstname("test");
        mockEmp.setSurname("name");
        mockEmp.setRole(mockRole1);
        Mockito.when(employeeRepo.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(mockEmp));
        Optional<Role> mockRole=Mockito.mock(Optional.class);
        Mockito.when(roleRepo.findById(ArgumentMatchers.anyLong())).thenReturn(mockRole);
        Mockito.when(operationMappers.dtoToemployee(ArgumentMatchers.any())).thenReturn(mockEmp);
        service.updateEmployee(1L, mockDto);
    }

    @Test
    void deleteEmployee() {
        Optional<Employee> mockOpt=Mockito.mock(Optional.class);
        Employee mockEmp=Mockito.mock(Employee.class);
        Role mockRole=Mockito.mock(Role.class);
        Mockito.when(employeeRepo.findById(ArgumentMatchers.anyLong())).thenReturn(mockOpt);
        Mockito.when(mockOpt.orElseThrow(ArgumentMatchers.any())).thenReturn(mockEmp);
        Mockito.when(mockEmp.getRole()).thenReturn(mockRole);
        assertEquals(service.deleteEmployee(1L).getStatusCode().value(),200);
    }
}