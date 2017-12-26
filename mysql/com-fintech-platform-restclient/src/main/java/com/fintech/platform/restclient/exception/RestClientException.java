package com.fintech.platform.restclient.exception;

public class RestClientException extends RuntimeException{

	private static final long serialVersionUID = 1L;
    
	public RestClientException() {
	    super();
	}
	  
	public RestClientException(String msg, Throwable cause) {
	    super(msg);
	    super.initCause(cause);  
	}
	public RestClientException(String msg) {
	    super(msg);
	}
	
	public RestClientException(Throwable cause) {
	    super();
	    super.initCause(cause);
	}
}
