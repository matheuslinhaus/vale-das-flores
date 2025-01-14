package com.valeflores.vale_das_flores.dto;

public class UpdatePasswordDTO {
	private String newPassword;
	private String oldPassword;

	public UpdatePasswordDTO(String newPassword, String oldPassword) {
		this.newPassword = newPassword;
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
}