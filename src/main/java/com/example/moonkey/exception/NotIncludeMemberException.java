package com.example.moonkey.exception;

public class NotIncludeMemberException extends RuntimeException {
    public NotIncludeMemberException() {
        super();
    }

    public NotIncludeMemberException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotIncludeMemberException(String message) {
        super(message);
    }

    public NotIncludeMemberException(Throwable cause) {
        super(cause);
    }
}
