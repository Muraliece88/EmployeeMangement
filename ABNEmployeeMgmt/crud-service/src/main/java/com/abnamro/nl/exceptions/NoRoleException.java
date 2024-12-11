package com.abnamro.nl.exceptions;

/**
 *
 * Custom exception when no matching role is found
 */
public class NoRoleException extends IllegalArgumentException {
    public NoRoleException(String message) {
        super(message);
    }
}
