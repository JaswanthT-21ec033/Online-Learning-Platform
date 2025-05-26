package com.springboot.onlinelearning.exception;

public class InvalidContactException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6466357084178582236L;
	
	private String message;

	public InvalidContactException(String message) {
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
