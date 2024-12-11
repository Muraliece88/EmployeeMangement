package com.abnamro.nl.mapper;

import com.abnamro.nl.constants.RolesEnum;
import com.abnamro.nl.dto.EmployeeRequestDto;
import com.abnamro.nl.dto.EmployeeResponseDto;
import com.abnamro.nl.entities.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Arrays;
import java.util.Optional;


/**
 * Custom mapping configuration
 */
@Mapper(componentModel = "spring")
public interface OperationMappers {

    /**
     * Converting request object to entity
     * @param employeeRequestDto
     * @return
     */

    @Mapping(target = "firstname", source="name", qualifiedByName = "employeeFirstName")
    @Mapping(target = "surname", source="name", qualifiedByName = "employeeLastName")
    @Mapping(target = "role.name", source=".",qualifiedByName = "getRoleName")
    @Mapping(target = "role.id", source="role_id")
    Employee dtoToemployee(EmployeeRequestDto employeeRequestDto);

    /**
     * Map the role name and to persists in role table
     * @param employeeRequestDto
     * @return
     */

    @Named("getRoleName")
    default String getRoleName(EmployeeRequestDto employeeRequestDto){
        RolesEnum roleEnum=Arrays.stream(RolesEnum.values()).filter(role->role.label==employeeRequestDto.role_id())
                .findFirst().orElseThrow(()-> new IllegalArgumentException("No matching role"));
        return roleEnum.name();
    }

    /**
     * To persists employee firstname from full name received in the request
     * @param name
     * @return
     */
    @Named("employeeFirstName")
    default String employeeFirstName(String name)
    {
        if(name.isBlank() || !name.contains(" "))
        {
            return name;
        }
        return name.substring(0,name.indexOf(" ")).trim();
    }

    /**
     * To persists employee Lastname from full name received in the request
     * @param name
     * @return
     */
    @Named("employeeLastName")
    default String employeeLastName(String name)
    {
        if(!name.contains(" "))
        {
            return "";
        }
        return name.substring(name.indexOf(" ")+1).trim();
    }


    @Mapping(target = "name", source=".", qualifiedByName = "employeeFullName")
    @Mapping(target = "role_id",  source=".", qualifiedByName = "getRoleId")
    EmployeeResponseDto employeeToresponse(Employee employee);

    @Named("employeeFullName")
    default String employeeFullName(Employee employee){
        return Optional.ofNullable(employee.getSurname()).
                filter(surname -> !surname.isBlank()).
                map(surname->(employee.getFirstname()+ " "+surname).trim()).
                orElse(employee.getFirstname());
    }
    @Named("getRoleId")
    default Long getRoleId(Employee employee){
        return employee.getRole().getId();
    }

}

