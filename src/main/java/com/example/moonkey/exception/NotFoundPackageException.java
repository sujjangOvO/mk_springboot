package com.example.moonkey.exception;

public class NotFoundPackageException extends RuntimeException{
    public NotFoundPackageException(){super();}
    public NotFoundPackageException(String message, Throwable cause){super(message,cause);}
    public NotFoundPackageException(String message){super(message);}
    public NotFoundPackageException(Throwable cause){super(cause);}
}
