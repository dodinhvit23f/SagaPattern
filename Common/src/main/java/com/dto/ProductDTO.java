package com.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Value;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductDTO {

	@NonNull
	@Size(min = 1)
	private String productName;
	
	@NonNull
	@Size(min = 1)
	private String productDescription;
	
	@Min(1)
	private double productPrice;
	
	@Min(0)
	@Value("${some.key:0}")
	private short productPromotion;

	@Min(1)
	private long quantity;
}
