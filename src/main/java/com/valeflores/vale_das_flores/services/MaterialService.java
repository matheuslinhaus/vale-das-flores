package com.valeflores.vale_das_flores.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.valeflores.vale_das_flores.entities.Material;
import com.valeflores.vale_das_flores.repositories.MaterialRepository;
import com.valeflores.vale_das_flores.services.exceptions.ResourceNotFoundException;

@Service
public class MaterialService {

	@Autowired
	private MaterialRepository repository;

//	@Autowired
//	private MessageSource messageSource;

	@Transactional
	public Material insert(Material material) {
		return repository.save(material);
	}
	
	public List<Material> findAll() {
		return repository.findAll();
	}
	
	@Transactional
	public Material update(Material material, Long id) {
		Material entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Material com ID " + id + " não encontrado."));
		entity.setTypeMaterial(material.getTypeMaterial());
		return repository.save(entity);
	}
	
	public void delete(Long id) {
	    if (!repository.existsById(id)) {
	        throw new ResourceNotFoundException("Material com ID " + id + " não encontrado.");
	    }
	    repository.deleteById(id);
	}
}