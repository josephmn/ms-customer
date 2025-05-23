package com.nttdata.customer.exception.types;

import java.util.Map;
import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException {
    private final Map<Integer, String> errors;

    /**
     * Constructor for ValidationException.
     *
     * @param errors the detail message
     */
    public ValidationException(Map<Integer, String> errors) {
        this.errors = errors;
    }
}
