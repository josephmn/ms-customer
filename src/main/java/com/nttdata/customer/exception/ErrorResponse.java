package com.nttdata.customer.exception;

import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class ErrorResponse {
    private int status;
    private String message;
    private Date date;
    private Map<Integer, String> errors;

    public ErrorResponse(int status, String message, Date date, Map<Integer, String> errors) {
        this.status = status;
        this.message = message;
        this.date = date;
        this.errors = errors;
    }

    public ErrorResponse(int status, String message, Date date) {
        this.status = status;
        this.message = message;
        this.date = date;
    }
}
