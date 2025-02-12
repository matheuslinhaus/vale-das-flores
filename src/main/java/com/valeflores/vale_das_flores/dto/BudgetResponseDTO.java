package com.valeflores.vale_das_flores.dto;

import com.valeflores.vale_das_flores.entities.enums.StatusBudget;

public class BudgetResponseDTO {

	private Long id;
	private String title;
	private String description;
	private String image;
	private StatusBudget status;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public StatusBudget getStatus() {
		return status;
	}
	public void setStatus(StatusBudget status) {
		this.status = status;
	}

}

