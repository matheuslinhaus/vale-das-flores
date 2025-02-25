package com.valeflores.vale_das_flores.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.valeflores.vale_das_flores.entities.ProductType;
import com.valeflores.vale_das_flores.repositories.ProductTypeRepository;
import com.valeflores.vale_das_flores.services.exceptions.ResourceNotFoundException;

@Service
public class ProductTypeService {

	@Autowired
	private ProductTypeRepository repository;

//	@Autowired
//	private MessageSource messageSource;

	@Transactional
	public ProductType insert(ProductType type) {
		return repository.save(type);
	}
	
	public List<ProductType> findAll() {
		return repository.findAll();
	}
	
	@Transactional
	public ProductType update(ProductType productType, Long id) {
		ProductType entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de produto com ID " + id + " não encontrado."));
		entity.setName(productType.getName());
		return repository.save(entity);
	}
	
	public void delete(Long id) {
	    if (!repository.existsById(id)) {
	        throw new ResourceNotFoundException("Tipo de produto com ID " + id + " não encontrado.");
	    }
	    repository.deleteById(id);
	}
}