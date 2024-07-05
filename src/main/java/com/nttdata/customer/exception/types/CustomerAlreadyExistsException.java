package com.nttdata.customer.exception.types;

public class CustomerAlreadyExistsException extends RuntimeException {

    public CustomerAlreadyExistsException(String message, Object... args) {
        super(String.format(message, args));
    }
}
