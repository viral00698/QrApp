package com.QrApplication.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class ProductList {
	
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Id
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String name;
	
	private String img;

}
