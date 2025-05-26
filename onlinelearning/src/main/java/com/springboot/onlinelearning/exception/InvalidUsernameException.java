package com.springboot.onlinelearning.exception;

public class InvalidUsernameException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9094507504732056096L;
	
	private String message;

	public InvalidUsernameException(String message) {
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
