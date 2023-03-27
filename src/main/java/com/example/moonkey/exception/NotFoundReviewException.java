package com.example.moonkey.exception;

public class NotFoundReviewException extends RuntimeException {
    public NotFoundReviewException() {
        super();
    }

    public NotFoundReviewException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundReviewException(String message) {
        super(message);
    }

    public NotFoundReviewException(Throwable cause) {
        super(cause);
    }
}
