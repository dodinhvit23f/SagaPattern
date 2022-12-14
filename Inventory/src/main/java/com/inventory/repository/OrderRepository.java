package com.inventory.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inventory.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, String>{
	public Optional<Order> findByCode(String code);
}
