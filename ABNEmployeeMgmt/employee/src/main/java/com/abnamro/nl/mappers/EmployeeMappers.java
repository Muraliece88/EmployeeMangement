package com.abnamro.nl.mappers;

import com.abnamro.nl.constants.RolesEnum;
import com.abnamro.nl.dtos.CreateDto;
import com.abnamro.nl.dtos.CreateResponseDto;
import com.abnamro.nl.dtos.EmployeeRequestDto;
import com.abnamro.nl.dtos.EmployeeResponseDto;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Optional;


/**
 * Custom mappers to consume from employee API and convert as expected in response
 */
@Mapper(componentModel = "spring")
public interface EmployeeMappers {
    EmployeeMappers INSTANCE = Mappers.getMapper(EmployeeMappers.class);


    /**
     * Mapping method to convert the request as expected by the operations API
     * @param employeeRequestDto
     * @param role
     * @return
     */
    @Mapping(target = "name", source=".", qualifiedByName = "employeeFullName")
    @Mapping(target = "role_id", expression="java(getRoleId(role))")
    CreateDto mapNameRole(EmployeeRequestDto employeeRequestDto, @Context String role);

    @Named("employeeFullName")
    default String employeeFullName(EmployeeRequestDto employeeRequestDto){
        return Optional.ofNullable(employeeRequestDto.surName()).
                filter(surname -> !surname.isBlank()).
                map(surname->employeeRequestDto.firstName()+ " "+surname).
                orElse(employeeRequestDto.firstName());

    }
    default Long getRoleId(String role){
        return RolesEnum.valueOf(role.toUpperCase()).label;
    }

    /**
     * mapping method to convert the response from operations API to the actual response
     * @param createResponseDto
     * @return
     */

    @Mapping(source =".",target ="firstName", qualifiedByName ="employeeFirstName" )
    @Mapping(source =".",target ="surname", qualifiedByName ="employeeLastName" )
    EmployeeResponseDto mapToResponse(CreateResponseDto createResponseDto);


    @Named("employeeFirstName")
    default String employeeFirstName(CreateResponseDto createResponseDto)
    {
        if(createResponseDto.name().isBlank() || !createResponseDto.name().contains(" "))
        {
            return createResponseDto.name();
        }
        return createResponseDto.name().substring(0,createResponseDto.name().indexOf(" ")).trim();
    }
    @Named("employeeLastName")
    default String employeeLastName(CreateResponseDto createResponseDto)
    {
        if(!createResponseDto.name().contains(" "))
        {
            return "";
        }
        return createResponseDto.name().substring(createResponseDto.name().indexOf(" ")+1).trim();
    }

}
