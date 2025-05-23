package com.nttdata.customer.exception;

import java.util.Date;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.nttdata.customer.exception.types.CustomerAlreadyExistsException;
import com.nttdata.customer.exception.types.NotFoundException;
import com.nttdata.customer.exception.types.ValidationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle NotFoundException and return a 404 response.
     *
     * @param exception the NotFoundException
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException exception) {
        final ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            exception.getMessage(),
            new Date()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    /**
     * Handle ValidationException and return a 400 response.
     *
     * @param exception the ValidationException
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(ValidationException exception) {
        final ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            "Error validation",
            new Date(),
            exception.getErrors()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle CustomerAlreadyExistsException and return a 409 response.
     *
     * @param exception the CustomerAlreadyExistsException
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(CustomerAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorResponse> handleNotFoundException(CustomerAlreadyExistsException exception) {
        final ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.CONFLICT.value(),
            exception.getMessage(),
            new Date()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }
}
