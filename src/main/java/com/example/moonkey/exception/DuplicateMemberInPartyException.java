package com.example.moonkey.exception;

public class DuplicateMemberInPartyException extends RuntimeException{
    public DuplicateMemberInPartyException() {
        super();
    }
    public DuplicateMemberInPartyException(String message, Throwable cause) {
        super(message, cause);
    }
    public DuplicateMemberInPartyException(String message) {
        super(message);
    }
    public DuplicateMemberInPartyException(Throwable cause) {
        super(cause);
    }
}
