package com.abnamro.nl.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;


/**
 * Custom error handling
 */
@Data
@AllArgsConstructor
public class Errors {
    private int status;
    private String message;
    private LocalDateTime timeStamp;
}
