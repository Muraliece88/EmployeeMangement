package com.abnamro.nl.exceptions;

/**
 * Custom exception when no matching employee found
 */
public class EmployeeNotFoundException extends  RuntimeException {
    public EmployeeNotFoundException(String message) {
        super(message);
    }
}
