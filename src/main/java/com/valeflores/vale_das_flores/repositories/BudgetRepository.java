package com.valeflores.vale_das_flores.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.valeflores.vale_das_flores.entities.Budget;
import com.valeflores.vale_das_flores.entities.enums.StatusBudget;


public interface BudgetRepository extends JpaRepository<Budget, Long>{
	
	@Query(value = "SELECT * FROM TB_BUDGET WHERE USER_ID = :userId", nativeQuery = true)
	List<Budget> findByUserId(@Param("userId") Long userId);

	@Query(value = "SELECT * FROM TB_BUDGET WHERE USER_ID = :userId AND STATUS = :status", nativeQuery = true)
	List<Budget> findByUserIdAndStatus(@Param("userId") Long userId, @Param("status") StatusBudget status);

}
