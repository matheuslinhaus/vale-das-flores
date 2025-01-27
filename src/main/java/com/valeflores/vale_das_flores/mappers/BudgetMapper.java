package com.valeflores.vale_das_flores.mappers;

import java.io.IOException;
import java.util.Base64;

import com.valeflores.vale_das_flores.dto.BudgetCreateDTO;
import com.valeflores.vale_das_flores.dto.BudgetResponseDTO;
import com.valeflores.vale_das_flores.entities.Budget;
import com.valeflores.vale_das_flores.entities.enums.StatusBudget;

public class BudgetMapper {

	public static Budget toEntity(BudgetCreateDTO dto) throws IOException {
		Budget budget = new Budget();
		budget.setTitle(dto.getTitle());
		budget.setDescription(dto.getDescription());
		budget.setImage(dto.getImage().getBytes());
		budget.setStatus(StatusBudget.REQUESTED);
		return budget;
	}

	public static BudgetResponseDTO toResponseDTO(Budget budget) {
		BudgetResponseDTO dto = new BudgetResponseDTO();
		dto.setId(budget.getId());
		dto.setTitle(budget.getTitle());
		dto.setDescription(budget.getDescription());
		dto.setStatus(budget.getStatus());

		// Converte a imagem em Base64, se existir
		if (budget.getImage() != null && budget.getImage().length > 0) {
			String base64Image = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(budget.getImage());
			dto.setImage(base64Image);
		}

		return dto;
	}
}