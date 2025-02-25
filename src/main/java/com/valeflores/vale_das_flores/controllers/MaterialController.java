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

import com.valeflores.vale_das_flores.entities.Material;
import com.valeflores.vale_das_flores.services.MaterialService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/admin/material")
public class MaterialController {

	@Autowired
	private MaterialService service;

//	@Autowired
//	private MessageSource messageSource;

	@GetMapping(value = "/", produces = "application/json")
	public ResponseEntity<List<Material>> findAll() {
		List<Material> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

	@PostMapping(value = "/", produces = "application/json")
	public ResponseEntity<Material> createMaterial(@RequestBody Material material) {
		Material type = service.insert(material);
		return ResponseEntity.status(HttpStatus.CREATED).body(type);
	}
	
	@PutMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<Material> updateMaterial(@PathVariable Long id, @RequestBody Material material) {
		Material updateMaterial = service.update(material, id);
        return ResponseEntity.ok(updateMaterial);
	}
	
	@DeleteMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<Void> deleteMaterial(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
}
