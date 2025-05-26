package com.springboot.onlinelearning.exception;

public class InvalidIDException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5340912710872395057L;
	
	private String message;

	public InvalidIDException(String message) {
		super();
		this.message = message;
	}
	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	

}
