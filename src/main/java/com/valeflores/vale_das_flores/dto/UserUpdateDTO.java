package com.valeflores.vale_das_flores.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class UserUpdateDTO {
	@NotEmpty(message = "The name is required and cannot be empty.")
	@Size(min = 8, max = 300, message = "The name must be between 8 and 300 characters long.")
	private String name;

	@NotEmpty(message = "The e-mail is required and cannot be empty.")
	@Email(message = "Invalid email format.")
	private String email;
	
	private String phone;

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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
