package com.valeflores.vale_das_flores.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.valeflores.vale_das_flores.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{
	boolean existsByEmail(String email);

}
