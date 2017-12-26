package com.fintech.platform.core.message;

import java.io.Serializable;

public class ResponseMsg<T> implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Header header = new Header();

	private T responseBody;
	
	//获取globalID
	/*private String status;
	private String message;
	private String[] values;
	private String[] codeValues;*/
	
	/*public String[] getValues() {
		return values;
	}
	public void setValues(String[] values) {
		this.values = values;
	}
	public String[] getCodeValues() {
		return codeValues;
	}
	public void setCodeValues(String[] codeValues) {
		this.codeValues = codeValues;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	*/
	public T getResponseBody() {
		return responseBody;
	}
	
	public void setResponseBody(T responseBody) {
		this.responseBody = responseBody;
	}

	public String getRetCode() {
		return header.retCode;
	}

	public void setRetCode(String retCode) {
		header.retCode = retCode;
	}

	public String getErrorDesc() {
		return header.errorDesc;
	}

	public void setErrorDesc(String errorDesc) {
		header.errorDesc = errorDesc;
	}
	
	 private final class Header{
		
		String retCode = ResponseStatus.HTTP_OK;
		 
		String errorDesc = null;
		 
	}
	
	 @Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Header [retCode=");
		builder.append(getRetCode());
		builder.append(", errorDesc=");
		builder.append(getErrorDesc());
		builder.append("]");
		builder.append("   Body [");
		if(responseBody!=null) {
			builder.append(responseBody.toString());
		}
		builder.append("]");
		return builder.toString();
	}


}
