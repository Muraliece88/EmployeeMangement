package com.abnamro.nl.controller;

import com.abnamro.nl.dto.EmployeeRequestDto;
import com.abnamro.nl.dto.EmployeeResponseDto;
import com.abnamro.nl.entities.Employee;
import com.abnamro.nl.entities.Role;
import com.abnamro.nl.mapper.OperationMappers;
import com.abnamro.nl.repo.EmployeeRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestClient;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class OperationsControllerTest {
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;
    @Mock
    private RestClient restClient;
    @Mock
    private RestClient.RequestBodyUriSpec bodyUriSpec;
    @Mock
    private RestClient.ResponseSpec mocResponse;
    @MockBean
    private EmployeeRepo employeeRepo;
    @Mock
    private OperationMappers operationMappers;
    @Mock
    private EmployeeRequestDto employeeRequestDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
    }
    @Test
    @WithMockUser(username = "dummy", password = "password")
    void createEmployee() throws Exception {
        mockMvc.perform(post("/api/employees/")
                        .content("{\n" +
                                "    \"name\": \"John Joe\",\n" +
                                "    \"role_id\": 1\n" +
                                "}")

                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201));
    }

    @Test
    @WithMockUser(username = "dummy", password = "password")
    void getEmployeeDetails() throws Exception {
        Optional<Employee> mockOpt=Mockito.mock(Optional.class);
        Employee mockEmp=Mockito.mock(Employee.class);
        Role mockRole=Mockito.mock(Role.class);
        EmployeeResponseDto EmployeeResponseDto=Mockito.mock(EmployeeResponseDto.class);
        Mockito.when(employeeRepo.findById(1L)).thenReturn(mockOpt);
        Mockito.when(mockOpt.isEmpty()).thenReturn(false);
        Mockito.when(mockOpt.isPresent()).thenReturn(true);
        Mockito.when(mockOpt.get()).thenReturn(mockEmp);
        Mockito.when(mockEmp.getRole()).thenReturn(mockRole);
        Mockito.when(operationMappers.employeeToresponse(mockEmp)).thenReturn(EmployeeResponseDto);
        mockMvc.perform(get("/api/employees/{Id}",1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    @WithMockUser(username = "dummy", password = "password")
    void updateEmployeeDetails() throws Exception {
        Optional<Employee> mockOpt=Mockito.mock(Optional.class);
        Set<Employee> empSet=new HashSet<>();
        Employee mockEmp=new Employee();
        Role mockRole=new Role();
        mockRole.setName("ADMIN");
        mockRole.setId(1L);
        mockEmp.setId(1L);
        mockEmp.setFirstname("John");
        mockEmp.setSurname("Joe");
        mockEmp.setRole(mockRole);
        empSet.add(mockEmp);
        mockRole.setEmployee(empSet);
        EmployeeResponseDto EmployeeResponseDto=Mockito.mock(EmployeeResponseDto.class);
        Mockito.when(employeeRepo.findById(1L)).thenReturn(Optional.of(mockEmp));
        Mockito.when(mockOpt.orElseThrow()).thenReturn(mockEmp);
        Mockito.when(operationMappers.employeeToresponse(mockEmp)).thenReturn(EmployeeResponseDto);
        mockMvc.perform(put("/api/employees/{Id}",1L)
                        .content("{\n" +
                                "    \"name\": \"John\",\n" +
                                "    \"role_id\": 1\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));

    }

    @Test
    @WithMockUser(username = "dummy", password = "password")
    void deleteEmployeeDetails() throws Exception {
        Optional<Employee> mockOpt=Mockito.mock(Optional.class);
        Set<Employee> empSet=new HashSet<>();
        Employee mockEmp=new Employee();
        Role mockRole=new Role();
        mockRole.setName("ADMIN");
        mockRole.setId(1L);
        mockEmp.setId(1L);
        mockEmp.setFirstname("John");
        mockEmp.setSurname("Joe");
        mockEmp.setRole(mockRole);
        empSet.add(mockEmp);
        mockRole.setEmployee(empSet);
        EmployeeResponseDto EmployeeResponseDto=Mockito.mock(EmployeeResponseDto.class);
        Mockito.when(employeeRepo.findById(1L)).thenReturn(Optional.of(mockEmp));
        Mockito.when(mockOpt.orElseThrow()).thenReturn(mockEmp);
        Mockito.when(operationMappers.employeeToresponse(mockEmp)).thenReturn(EmployeeResponseDto);
        mockMvc.perform(delete("/api/employees/{Id}",1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }
}