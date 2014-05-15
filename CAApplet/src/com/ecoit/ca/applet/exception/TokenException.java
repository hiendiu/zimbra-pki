package com.ecoit.ca.applet.exception;

public class TokenException extends Exception {

	public TokenException(String message) {
		super(message);
	}

	public TokenException(Exception e) {
		super(e.getCause());
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
