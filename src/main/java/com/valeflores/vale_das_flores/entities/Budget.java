package com.valeflores.vale_das_flores.entities;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.valeflores.vale_das_flores.entities.enums.StatusBudget;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity(name= "tb_budget")
public class Budget {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;
	private String description;

	@Enumerated(EnumType.ORDINAL)
	private StatusBudget status;

	@Column(updatable = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
	private Instant budgetDay = Instant.now();

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT")
	private Instant dueDate; 

    @Lob
    @Column(name = "image", columnDefinition = "BLOB")
    private byte[] image;

	public Budget() {
	}

	public Budget(String title, String description, byte[] image) {
		this.title = title;
		this.description = description;
		this.image = image;
	}

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

	public StatusBudget getStatus() {
		return status;
	}

	public void setStatus(StatusBudget status) {
		this.status = status;
	}

	public Instant getBudgetDay() {
		return budgetDay;
	}

	public void setBudgetDay(Instant budgetDay) {
		this.budgetDay = budgetDay;
	}

	public Instant getDueDate() {
		return dueDate;
	}

	public void setDueDate(Instant dueDate) {
		this.dueDate = dueDate;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

}
