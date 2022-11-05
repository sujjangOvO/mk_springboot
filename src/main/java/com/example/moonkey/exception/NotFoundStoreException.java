package com.example.moonkey.exception;

public class NotFoundStoreException extends RuntimeException{
	public NotFoundStoreException(){super();}
	public NotFoundStoreException(String message, Throwable cause){super(message,cause);}
	public NotFoundStoreException(String message){super(message);}
	public NotFoundStoreException(Throwable cause){super(cause);}
}
