package com.nttdata.customer.exception.types;

public class NotFoundException extends RuntimeException {

    /**
     * Constructor for NotFoundException.
     *
     * @param message the detail message
     * @param args    the arguments to format the message
     */
    public NotFoundException(String message, Object... args) {
        super(String.format(message, args));
    }
}
