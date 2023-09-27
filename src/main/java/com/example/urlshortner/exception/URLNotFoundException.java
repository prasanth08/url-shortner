package com.example.urlshortner.exception;

/**
 * Exception for all URL not found errors
 *
 * @author  Prasanth Omanakuttan
 * @version 1.0
 * @since   2023-09-27
 */
public class URLNotFoundException extends RuntimeException{
    public URLNotFoundException(String message) {
        super(message);
    }

    public URLNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
