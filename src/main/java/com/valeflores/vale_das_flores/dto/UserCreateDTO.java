package com.valeflores.vale_das_flores.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class UserCreateDTO {

	@NotEmpty(message = "The name is required and cannot be empty.")
	@Size(min = 8, max = 300, message = "The name must be between 8 and 300 characters long.")
	private String name;

	@NotEmpty(message = "The e-mail is required and cannot be empty.")
	@Email(message = "Invalid email format.")
	private String email;

	@NotEmpty(message = "The password is required and cannot be empty.")
	@Size(min = 8, message = "The password must be at least 8 characters long.")
	private String password;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
