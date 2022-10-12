package com.inventory.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "orders", 
	indexes = { @Index(name = "ux_code", columnList = "code", unique = true) })
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Length(max = 40)
	private String code;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pro_id")
	private Product product;

	private Double price;

	private short promotion;

	private String status;

	private boolean isDeleted;
}
