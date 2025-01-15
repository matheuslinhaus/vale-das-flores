package com.valeflores.vale_das_flores.services.exceptions;

public class LoginAttemptException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public LoginAttemptException(String msg) {
		super(msg);
	}
}