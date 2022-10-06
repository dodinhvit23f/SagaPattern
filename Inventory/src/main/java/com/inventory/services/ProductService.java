package com.inventory.services;

import java.util.Date;
import java.util.Optional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dto.ProductDTO;
import com.inventory.entity.Product;
import com.inventory.repository.ProductRepository;

@Service
public class ProductService implements IProductService {

	@Autowired
	private ProductRepository productRepository;

	@Override
	public ProductDTO insertNewProduct(ProductDTO product, long retailerId)
			throws EntityExistsException {
		if (!productRepository
				.findByNameAndRetailerId(product.getProductName()
				, Long.valueOf(retailerId)).isEmpty()) {
			throw new EntityExistsException("Product is exist");
		}
		
		Product newProduct = new Product();
		newProduct.setDeleted(false);
		newProduct.setCreateDate(new Date());
		newProduct.setDescription(product.getProductDescription());
		newProduct.setName(product.getProductName());
		newProduct.setQuantity(product.getQuantity());
		newProduct.setPromotion(product.getProductPromotion());
		newProduct.setPrice(product.getProductPrice());
		
		return product;
	}

	@Override
	public ProductDTO insertExistProduct(long additionQuantity, long productId)
			throws EntityNotFoundException, IllegalArgumentException {
		Optional<Product> searchedProduct  = productRepository.findById(Long.valueOf(productId));
		
		if(searchedProduct.isEmpty()) {
			throw new EntityNotFoundException("Product not exist");
		}
		
		if(additionQuantity < 1) {
			throw new IllegalArgumentException("Addition quantity must be positive");
		}
		
		Product product = searchedProduct.get();
		
		return ProductDTO.builder()
				.productName(product.getName())
				.productPrice(product.getPrice())
				.productPromotion(product.getPromotion())
				.quantity(product.getQuantity())
				.build();
	}

	@Override
	public Optional<Product> findByIdAndRetailerId(long id, long retailerId) {
		return productRepository.findByIdAndRetailerId(id, retailerId);
	}


}
