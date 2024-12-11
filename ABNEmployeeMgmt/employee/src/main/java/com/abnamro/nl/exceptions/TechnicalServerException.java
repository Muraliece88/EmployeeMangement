package com.abnamro.nl.exceptions;


/**
 * Custom exception when operations API fail due to some internal technical reasons
 */
public class TechnicalServerException extends RuntimeException {
    public TechnicalServerException(String message){
        super(message);
    }

}
