package com.ssm.util;

public class CustomException extends Exception{
	
	private static final long serialVersionUID = -6963540752319598362L;
	
	private String message;

	public CustomException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	

}
