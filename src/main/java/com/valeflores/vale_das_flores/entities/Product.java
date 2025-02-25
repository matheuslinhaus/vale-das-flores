package com.valeflores.vale_das_flores.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_product")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String description;
	private String fullDescription;
	private Double price;
	private String season;

	@ManyToOne
	@JoinColumn(name = "product_type_id")
	private ProductType productType;

	@ManyToOne
	@JoinColumn(name = "color_id")
	private PredominantColor color;

	@ManyToOne
	@JoinColumn(name = "material_id")
	private Material material;

	private String urlImage;

	public Product() {
	}

	public Product(Long id, String description, String fullDescription, Double price, String season,
			ProductType productType, PredominantColor color, Material material, String urlImage) {
		this.id = id;
		this.description = description;
		this.fullDescription = fullDescription;
		this.price = price;
		this.season = season;
		this.productType = productType;
		this.color = color;
		this.material = material;
		this.urlImage = urlImage;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFullDescription() {
		return fullDescription;
	}

	public void setFullDescription(String fullDescription) {
		this.fullDescription = fullDescription;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}

	public PredominantColor getColor() {
		return color;
	}

	public void setColor(PredominantColor color) {
		this.color = color;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public String getUrlImage() {
		return urlImage;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}

}
