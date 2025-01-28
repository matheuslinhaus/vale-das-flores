package com.valeflores.vale_das_flores.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.valeflores.vale_das_flores.config.JwtUtil;
import com.valeflores.vale_das_flores.dto.BudgetCreateDTO;
import com.valeflores.vale_das_flores.dto.BudgetResponseDTO;
import com.valeflores.vale_das_flores.entities.Budget;
import com.valeflores.vale_das_flores.entities.User;
import com.valeflores.vale_das_flores.mappers.BudgetMapper;
import com.valeflores.vale_das_flores.services.BudgetService;
import com.valeflores.vale_das_flores.services.UserService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/budgets")
public class BudgetController {

	@Autowired
	private BudgetService service;

	@Autowired
	private UserService serviceUser;

	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping(consumes = "multipart/form-data")
	public ResponseEntity<?> createBudget(@RequestHeader("Authorization") String token,
			@ModelAttribute BudgetCreateDTO budgetDTO) {
		try {
			token = token.replace("Bearer ", "");
			jwtUtil.isTokenExpired(token);
			String email = jwtUtil.extractUsername(token);
			User user = serviceUser.findByEmail(email);

			Budget budget = BudgetMapper.toEntity(budgetDTO);

			budget.setUser(user);
			budget = service.insert(budget);

			BudgetResponseDTO responseDTO = BudgetMapper.toResponseDTO(budget);

			return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Erro ao processar a solicitação. Tente novamente mais tarde.");
		}
	}

	@GetMapping
	public ResponseEntity<?> getAllBudgetsUser(@RequestHeader("Authorization") String token) {
		try {
			token = token.replace("Bearer ", "");
			jwtUtil.isTokenExpired(token);
			String email = jwtUtil.extractUsername(token);
			User user = serviceUser.findByEmail(email);
			user.getId();
			List<Budget> budgets = service.findByUserId(user.getId());
			List<BudgetResponseDTO> responseDTOs = budgets.stream().map(BudgetMapper::toResponseDTO).toList();
			return ResponseEntity.ok(responseDTOs);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Erro ao buscar os orçamentos. Tente novamente mais tarde.");
		}
	}
}
