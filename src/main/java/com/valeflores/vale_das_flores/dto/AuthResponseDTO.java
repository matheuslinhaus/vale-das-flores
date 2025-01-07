package com.valeflores.vale_das_flores.dto;

public class AuthResponseDTO {

	private String token;

	public AuthResponseDTO(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}