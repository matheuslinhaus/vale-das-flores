package com.valeflores.vale_das_flores.services.exceptions;

public class RegisterException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public RegisterException(String msg) {
		super(msg);
	}
}