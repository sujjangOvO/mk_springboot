package com.example.moonkey.exception;

public class NotFoundRiderException extends RuntimeException{
    public NotFoundRiderException() {
        super();
    }
    public NotFoundRiderException(String message, Throwable cause) {
        super(message, cause);
    }
    public NotFoundRiderException(String message) {
        super(message);
    }
    public NotFoundRiderException(Throwable cause) {
        super(cause);
    }
}
