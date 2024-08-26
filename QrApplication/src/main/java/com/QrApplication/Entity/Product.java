package com.QrApplication.Entity;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Builder
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID productId;
	
	@Column(nullable = false)
	private String itemName;
	
	private Integer quantity;
	private Integer gram;
	private Integer totalGram;
	private Integer totalQuantity;
	private String description;
	private Boolean status;
	
	@ManyToOne
	@JoinColumn(name = "id")
	@JsonBackReference
	private Users users;
	
}
