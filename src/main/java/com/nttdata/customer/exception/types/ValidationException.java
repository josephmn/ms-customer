package com.nttdata.customer.exception.types;

import lombok.Getter;

import java.util.Map;

@Getter
public class ValidationException extends RuntimeException {
    private final Map<Integer, String> errors;

    public ValidationException(Map<Integer, String> errors) {
        this.errors = errors;
    }
}
