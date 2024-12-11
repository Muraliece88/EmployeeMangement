package com.abnamro.nl.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public record EmployeeResponseDto(Long id, @JsonProperty("first_name") String firstName, String surname, long role_id) {
}
