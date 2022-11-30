package com.example.moonkey.exception;

public class NotFoundMenuException extends RuntimeException {
    public NotFoundMenuException() {
        super();
    }

    public NotFoundMenuException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundMenuException(String message) {
        super(message);
    }

    public NotFoundMenuException(Throwable cause) {
        super(cause);
    }
}
