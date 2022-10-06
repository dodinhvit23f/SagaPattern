package com.inventory.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "products", indexes = { @Index(columnList = "name", name = "uk_name", unique = true) })
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 50)
	private String name;

	private String description;

	private Double price;

	private short promotion;

	private Long quantity;

	@Temporal(TemporalType.DATE)
	@Column(name = "create_date")
	private Date createDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "update_date")
	private Date updateDate;

	@Column(name = "retailer_id")
	private Long retailerId;

	private boolean isDeleted;
}
