package com.example.moonkey.exception;

public class NotFoundPartyException extends RuntimeException{
    public NotFoundPartyException(){
        super();
    }

    public NotFoundPartyException(String message, Throwable cause){
        super(message,cause);
    }

    public NotFoundPartyException(String message){
        super(message);
    }

    public NotFoundPartyException(Throwable cause){
        super(cause);
    }
}
