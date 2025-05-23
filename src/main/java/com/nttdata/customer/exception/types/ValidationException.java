package com.nttdata.customer.exception.types;

import java.util.Map;
import lombok.Getter;

/**
 * Custom exception class for validation errors.
 * This exception is thrown when there are validation errors in the application.
 * @author Joseph Magallanes
 * @since 2025-05-23
 */
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
