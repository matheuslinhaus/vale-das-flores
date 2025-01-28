package com.valeflores.vale_das_flores.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.valeflores.vale_das_flores.entities.Budget;
import com.valeflores.vale_das_flores.entities.enums.StatusBudget;
import com.valeflores.vale_das_flores.repositories.BudgetRepository;

@Service
public class BudgetService {

	@Autowired
	private BudgetRepository repository;

	@Transactional
	public Budget insert(Budget budget) {
		return repository.save(budget);
	}

	public List<Budget> findAll() {
		return repository.findAll();
	}

	public List<Budget> findByUserId(Long userId) {
		return repository.findByUserId(userId);
	}

	public List<Budget> findByUserIdAndStatus(Long id, StatusBudget status) {
		return repository.findByUserIdAndStatus(id, status);
	}
}