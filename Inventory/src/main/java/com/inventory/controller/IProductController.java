package com.inventory.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.dto.ProductDTO;

public interface IProductController {
	
	@PostMapping("/")
	ProductDTO  insertNewProduct( @RequestBody @Valid ProductDTO product);
	
	@PutMapping("/")
	ProductDTO  updateProductQuantity(long additionQuantity);
	
}
