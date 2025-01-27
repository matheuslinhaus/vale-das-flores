package com.valeflores.vale_das_flores.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.valeflores.vale_das_flores.dto.BudgetCreateDTO;
import com.valeflores.vale_das_flores.dto.BudgetResponseDTO;
import com.valeflores.vale_das_flores.entities.Budget;
import com.valeflores.vale_das_flores.mappers.BudgetMapper;
import com.valeflores.vale_das_flores.services.BudgetService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/budgets")
public class BudgetController {

	@Autowired
	private BudgetService service;

	@PostMapping(consumes = "multipart/form-data")
	public ResponseEntity<?> createBudget(@ModelAttribute BudgetCreateDTO budgetDTO) {
		System.out.println(".");
		try {// Converte o DTO em entidade usando o Mapper
			Budget budget = BudgetMapper.toEntity(budgetDTO);

			// Salva o budget no banco de dados
			budget = service.insert(budget);

			// Converte a entidade para ResponseDTO
			BudgetResponseDTO responseDTO = BudgetMapper.toResponseDTO(budget);

			// Retorna sucesso
			return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
		} catch (Exception e) {
			// Loga o erro
			// log.error("Erro ao criar orçamento", e);

			// Retorna erro interno
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Erro ao processar a solicitação. Tente novamente mais tarde.");
		}

	}
	
	@GetMapping
	public ResponseEntity<?> getAllBudgets() {
	    try {
	        // Recupera todos os orçamentos do banco de dados
	        List<Budget> budgets = service.findAll();

	        // Converte cada entidade para um DTO de resposta
	        List<BudgetResponseDTO> responseDTOs = budgets.stream()
	                .map(BudgetMapper::toResponseDTO)
	                .toList();

	        // Retorna a lista de orçamentos com sucesso
	        return ResponseEntity.ok(responseDTOs);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("Erro ao buscar os orçamentos. Tente novamente mais tarde.");
	    }
	}

	

}
