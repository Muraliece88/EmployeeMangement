package com.abnamro.nl.dto;

import jakarta.validation.constraints.NotBlank;

public record EmployeeRequestDto(@NotBlank(message = "Name should not be blank") String name, Long role_id) {
}
