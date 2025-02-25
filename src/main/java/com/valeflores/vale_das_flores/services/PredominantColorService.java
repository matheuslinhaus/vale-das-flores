package com.valeflores.vale_das_flores.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.valeflores.vale_das_flores.entities.PredominantColor;
import com.valeflores.vale_das_flores.repositories.PredominantColorRepository;
import com.valeflores.vale_das_flores.services.exceptions.ResourceNotFoundException;

@Service
public class PredominantColorService {

	@Autowired
	private PredominantColorRepository repository;

//	@Autowired
//	private MessageSource messageSource;

	@Transactional
	public PredominantColor insert(PredominantColor color) {
		return repository.save(color);
	}
	
	public List<PredominantColor> findAll() {
		return repository.findAll();
	}
	
	@Transactional
	public PredominantColor update(PredominantColor color, Long id) {
		PredominantColor entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cor com ID " + id + " não encontrado."));
		entity.setHexadecimalColor(color.getHexadecimalColor());
		entity.setName(color.getName());
		return repository.save(entity);
	}
	
	public void delete(Long id) {
	    if (!repository.existsById(id)) {
	        throw new ResourceNotFoundException("Cor com ID " + id + " não encontrado.");
	    }
	    repository.deleteById(id);
	}
}