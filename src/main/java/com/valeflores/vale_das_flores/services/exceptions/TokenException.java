package com.valeflores.vale_das_flores.services.exceptions;

public class TokenException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public TokenException(String msg) {
		super(msg);
	}
}