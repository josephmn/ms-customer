package com.nttdata.customer.exception.types;

public class CustomerAlreadyExistsException extends RuntimeException {

    /**
     * Constructor for CustomerAlreadyExistsException.
     *
     * @param message the detail message
     */
    public CustomerAlreadyExistsException(String message, Object... args) {
        super(String.format(message, args));
    }
}
