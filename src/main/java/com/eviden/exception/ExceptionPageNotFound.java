package com.eviden.exception;

public class ExceptionPageNotFound extends RuntimeException {
	
	private static final long serialVersionUID = -409743470779314218L;

	public ExceptionPageNotFound(String  exception) {
		super(exception);
	}
}
