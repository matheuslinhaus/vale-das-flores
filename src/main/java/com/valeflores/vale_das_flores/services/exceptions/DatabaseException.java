package com.valeflores.vale_das_flores.services.exceptions;

public class DatabaseException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DatabaseException(String msg) {
		super(msg);
	}
}