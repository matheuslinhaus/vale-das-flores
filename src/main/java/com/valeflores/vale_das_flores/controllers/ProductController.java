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

import com.valeflores.vale_das_flores.entities.Product;
import com.valeflores.vale_das_flores.services.ProductService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/admin/product")
public class ProductController {

	@Autowired
	private ProductService service;

//	@Autowired
//	private MessageSource messageSource;

	@GetMapping(value = "/", produces = "application/json")
	public ResponseEntity<List<Product>> findAll() {
		List<Product> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

	@PostMapping(value = "/", produces = "application/json")
	public ResponseEntity<Product> createProduct(@RequestBody Product product) {
		Product newProduct = service.insert(product);
		return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
	}
	
	@PutMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        Product updated = service.update(product, id);
        return ResponseEntity.ok(updated);
	}
	
	@DeleteMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
}
