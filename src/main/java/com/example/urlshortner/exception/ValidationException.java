package com.example.urlshortner.exception;

/**
 * Validation Exception thrown in cases of all validation errors.
 *
 * @author  Prasanth Omanakuttan
 * @version 1.0
 * @since   2023-09-27
 */
 public class ValidationException extends RuntimeException{
    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
