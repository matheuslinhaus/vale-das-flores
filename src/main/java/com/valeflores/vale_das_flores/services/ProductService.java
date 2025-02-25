package com.valeflores.vale_das_flores.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.valeflores.vale_das_flores.entities.Product;
import com.valeflores.vale_das_flores.repositories.ProductRepository;
import com.valeflores.vale_das_flores.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;

//	@Autowired
//	private MessageSource messageSource;

	@Transactional
	public Product insert(Product product) {
		return repository.save(product);
	}
	
	public List<Product> findAll() {
		return repository.findAll();
	}
	
	@Transactional
	public Product update(Product product, Long id) {
	    Product entity = repository.findById(id)
	            .orElseThrow(() -> new ResourceNotFoundException("Produto com ID " + id + " não encontrado."));
	    entity.setDescription(product.getDescription());
	    entity.setFullDescription(product.getFullDescription());
	    entity.setPrice(product.getPrice());
	    entity.setSeason(product.getSeason());
	    entity.setProductType(product.getProductType());
	    entity.setColor(product.getColor());
	    entity.setMaterial(product.getMaterial());
	    entity.setUrlImage(product.getUrlImage());

	    return repository.save(entity);
	}

	
	public void delete(Long id) {
	    if (!repository.existsById(id)) {
	        throw new ResourceNotFoundException("Produto com ID " + id + " não encontrado.");
	    }
	    repository.deleteById(id);
	}
}