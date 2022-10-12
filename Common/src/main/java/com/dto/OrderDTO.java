package com.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Value;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class OrderDTO {
	@Min(1)
	private long productId;
	@Min(1)
	private long customerId;
	@Min(1)
	private long retailerId;
	@Min(1)
	private double price;
	@Min(0)
	@Max(100)
	@Value("${some.key:0}")
	private short  promotion;
	@Min(1)
	@Value("${some.key:1}")
	private long quantity;
	
	private String id;
	
	private String status;
}
