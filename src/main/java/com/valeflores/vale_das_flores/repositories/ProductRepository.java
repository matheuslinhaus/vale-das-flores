package com.valeflores.vale_das_flores.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.valeflores.vale_das_flores.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
