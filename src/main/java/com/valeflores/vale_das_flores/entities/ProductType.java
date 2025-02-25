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

@Entity(name = "tb_type_product")
public class ProductType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	@JsonIgnore
	@OneToMany(mappedBy = "productType", cascade = CascadeType.ALL)
    private List<Product> fields = new ArrayList<>();

	public ProductType() {
	}

	public ProductType(Long id, String name, List<Product> fields) {
		this.id = id;
		this.name = name;
		this.fields = fields;
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

	public List<Product> getFields() {
		return fields;
	}

	public void setFields(List<Product> fields) {
		this.fields = fields;
	}
	
	
}
