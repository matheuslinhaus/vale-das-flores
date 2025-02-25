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

import com.valeflores.vale_das_flores.entities.PredominantColor;
import com.valeflores.vale_das_flores.services.PredominantColorService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/admin/color")
public class PredominantColorController {

	@Autowired
	private PredominantColorService service;

//	@Autowired
//	private MessageSource messageSource;

	@GetMapping(value = "/", produces = "application/json")
	public ResponseEntity<List<PredominantColor>> findAll() {
		List<PredominantColor> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

	@PostMapping(value = "/", produces = "application/json")
	public ResponseEntity<PredominantColor> createColor(@RequestBody PredominantColor color) {
		PredominantColor type = service.insert(color);
		return ResponseEntity.status(HttpStatus.CREATED).body(type);
	}
	
	@PutMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<PredominantColor> updateColor(@PathVariable Long id, @RequestBody PredominantColor color) {
		PredominantColor updateColor = service.update(color, id);
        return ResponseEntity.ok(updateColor);
	}
	
	@DeleteMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<Void> deleteColor(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
}
