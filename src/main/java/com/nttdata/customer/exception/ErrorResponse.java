package com.nttdata.customer.exception;

import java.util.Date;
import java.util.Map;
import lombok.Data;

@Data
public class ErrorResponse {
    private int status; // HTTP status code
    private String message;
    private Date date;
    private Map<Integer, String> errors;

    /**
     * Constructor for ErrorResponse with status, message, date, and errors.
     *
     * @param status HTTP status code
     * @param message Error message
     * @param date Date of the error
     * @param errors Map of error codes and messages
     */
    public ErrorResponse(int status, String message, Date date, Map<Integer, String> errors) {
        this.status = status;
        this.message = message;
        this.date = date;
        this.errors = errors;
    }

    /**
     * Constructor for ErrorResponse with status, message, and date.
     *
     * @param status HTTP status code
     * @param message Error message
     * @param date Date of the error
     */
    public ErrorResponse(int status, String message, Date date) {
        this.status = status;
        this.message = message;
        this.date = date;
    }
}
