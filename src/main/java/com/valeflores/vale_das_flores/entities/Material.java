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
@Table(name = "tb_material")
public class Material {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String typeMaterial;

	@JsonIgnore
	@OneToMany(mappedBy = "material", cascade = CascadeType.ALL)
	private List<Product> fields = new ArrayList<>();

	public Material() {
	}

	public Material(Long id, String typeMaterial, List<Product> fields) {
		this.id = id;
		this.typeMaterial = typeMaterial;
		this.fields = fields;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTypeMaterial() {
		return typeMaterial;
	}

	public void setTypeMaterial(String typeMaterial) {
		this.typeMaterial = typeMaterial;
	}

	public List<Product> getFields() {
		return fields;
	}

	public void setFields(List<Product> fields) {
		this.fields = fields;
	}

}
