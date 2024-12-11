package com.abnamro.nl.exceptions;

import jakarta.validation.ConstraintDefinitionException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Global exception handlers
 */
@Slf4j
@RestControllerAdvice(basePackages = "com.abnamro.nl")
public class ExceptionHandlers {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Errors> handleException(Exception ex) {
        log.error("Exception occured when processing the request:"+ex.getLocalizedMessage());
        Errors errors=eveluateException(ex);
        return ResponseEntity.status(errors.getStatus()).body(errors);

    }
    private  Errors eveluateException(Exception ex)
    {
        if(ex instanceof EmployeeNotFoundException){
            return new Errors(HttpStatus.NOT_FOUND.value(),ex.getMessage(),LocalDateTime.now());
        }
        else if(ex instanceof NoRoleException){
            return new Errors(HttpStatus.NOT_FOUND.value(),ex.getMessage(),LocalDateTime.now());
        }
        else if(ex instanceof ConstraintViolationException){
            log.info("Constraint exception triggered");
            return new Errors(HttpStatus.BAD_REQUEST.value(),ex.getMessage(),LocalDateTime.now());
        }
        else if(ex instanceof MissingServletRequestParameterException){
            log.info("Missing param exception triggered");
            return new Errors(HttpStatus.BAD_REQUEST.value(),ex.getMessage(),LocalDateTime.now());
        }
        else if(ex instanceof ConstraintDefinitionException){
            log.info("Constraint definition exception triggered");
            return new Errors(HttpStatus.BAD_REQUEST.value(),ex.getMessage(),LocalDateTime.now());
        }
        else if(ex instanceof MethodArgumentNotValidException){
            log.info("Method argument exception triggered");
            return new Errors(HttpStatus.BAD_REQUEST.value(),ex.getMessage(),LocalDateTime.now());
        }
        else if(ex instanceof MethodArgumentTypeMismatchException){
            log.info("Method argument typemimatch triggered");
            return new Errors(HttpStatus.BAD_REQUEST.value(),ex.getMessage(),LocalDateTime.now());

        }
        else if(ex instanceof HandlerMethodValidationException){
            log.info("Method argument typemimatch triggered"+ex.getClass());
            return new Errors(HttpStatus.BAD_REQUEST.value(), Arrays.stream(((HandlerMethodValidationException) ex).getDetailMessageArguments()).collect(Collectors.toSet()).toString(),LocalDateTime.now());

        }
        else if(ex instanceof AccessDeniedException){
            log.info("Access denied triggered"+ex.getClass());
            return new Errors(HttpStatus.FORBIDDEN.value(), ex.getMessage(), LocalDateTime.now());

        }
        else if(ex instanceof AuthenticationException){
            log.info("Authorization exception triggered"+ex.getClass());
            return new Errors(HttpStatus.FORBIDDEN.value(), ex.getMessage(), LocalDateTime.now());

        }
        else {
            log.info("Generic exception triggered"+ex.getClass());
            return new Errors(HttpStatus.INTERNAL_SERVER_ERROR.value(),ex.getMessage(),LocalDateTime.now());
        }

    }
}
