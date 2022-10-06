package com.inventory.services;

import java.util.Optional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import com.dto.ProductDTO;
import com.inventory.entity.Product;

public interface IProductService {
	
	public ProductDTO insertNewProduct(ProductDTO product, long retailerId)  throws EntityExistsException;
	
	public ProductDTO insertExistProduct(long additionQuantity, long productId)  throws EntityNotFoundException, IllegalArgumentException;

	public Optional<Product> findByIdAndRetailerId(long id, long retailerId);
}
