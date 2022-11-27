package com.example.moonkey.exception;

public class PartyRunningException extends RuntimeException {

	public PartyRunningException(){super();}
	public PartyRunningException(String message, Throwable cause){super(message, cause);}
	public PartyRunningException(String message){super(message);}
	public PartyRunningException(Throwable cause){super(cause);}
}
