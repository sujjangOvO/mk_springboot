package com.example.moonkey.exception;

public class FullPartyNumException extends RuntimeException{
    public FullPartyNumException() {
        super();
    }
    public FullPartyNumException(String message, Throwable cause) {
        super(message, cause);
    }
    public FullPartyNumException(String message) {
        super(message);
    }
    public FullPartyNumException(Throwable cause) {
        super(cause);
    }
}
