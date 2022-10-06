package com.inventory.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inventory.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{	
	Optional<Product> findByNameAndRetailerId(String name, Long retailerId);
	Optional<Product> findByIdAndRetailerId(Long id, Long retailerId);
}
