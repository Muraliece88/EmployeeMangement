package com.abnamro.nl.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;


    public record EmployeeRequestDto(@JsonProperty("first_name") @NotBlank(message = "First Name cannot be blank") String firstName, @JsonProperty("surname") String surName) {
}
