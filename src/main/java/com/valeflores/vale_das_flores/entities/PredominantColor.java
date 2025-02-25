package com.valeflores.vale_das_flores.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_predominant_color")
public class PredominantColor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	private String hexadecimalColor;
	
	@JsonIgnore
	@OneToMany(mappedBy = "color", cascade = CascadeType.ALL)
    private List<Product> fields = new ArrayList<>();

	public PredominantColor() {
	}

	public PredominantColor(Long id, String name, String hexadecimalColor) {
		this.id = id;
		this.name = name;
		this.hexadecimalColor = hexadecimalColor;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHexadecimalColor() {
		return hexadecimalColor;
	}

	public void setHexadecimalColor(String hexadecimalColor) {
		this.hexadecimalColor = hexadecimalColor;
	}
	
}
