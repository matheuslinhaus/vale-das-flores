package com.valeflores.vale_das_flores.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.valeflores.vale_das_flores.entities.ProductType;
import com.valeflores.vale_das_flores.services.ProductTypeService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/admin/product-type")
public class ProductTypeController {

	@Autowired
	private ProductTypeService service;

//	@Autowired
//	private MessageSource messageSource;

	@GetMapping(value = "/", produces = "application/json")
	public ResponseEntity<List<ProductType>> findAll() {
		List<ProductType> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

	@PostMapping(value = "/", produces = "application/json")
	public ResponseEntity<ProductType> createProductType(@RequestBody ProductType productType) {
		ProductType type = service.insert(productType);
		return ResponseEntity.status(HttpStatus.CREATED).body(type);
	}
	
	@PutMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<ProductType> updateProductType(@PathVariable Long id, @RequestBody ProductType productType) {
        ProductType updatedType = service.update(productType, id);
        return ResponseEntity.ok(updatedType);
	}
	
	@DeleteMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<Void> deleteProductType(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
}
