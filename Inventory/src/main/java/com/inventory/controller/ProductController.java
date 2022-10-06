package com.inventory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dto.ProductDTO;
import com.inventory.services.IProductService;


@RestController
@RequestMapping("/api/v1/product")
public class ProductController implements IProductController {
	
	@Autowired
	private IProductService productService;
	
	@Override
	public ProductDTO insertNewProduct(ProductDTO product) {
		return productService.insertNewProduct(product, 2);
	}

	@Override
	public ProductDTO updateProductQuantity(long additionQuantity) {
		return productService.insertExistProduct(additionQuantity, 2l);
	}

}
