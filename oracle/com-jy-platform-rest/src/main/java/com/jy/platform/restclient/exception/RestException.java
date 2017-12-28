package com.jy.platform.restclient.exception;

public class RestException extends RuntimeException{

	private static final long serialVersionUID = 1L;
    
	public RestException() {
	    super();
	}
	  
	public RestException(String msg, Throwable cause) {
	    super(msg);
	    super.initCause(cause);  
	}
	public RestException(String msg) {
	    super(msg);
	}
	
	public RestException(Throwable cause) {
	    super();
	    super.initCause(cause);
	}
}
