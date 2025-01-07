package com.valeflores.vale_das_flores.services.exceptions;

public class ResourceNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(Object id) {
		super("Resource not found. Id " + id);
	}
}