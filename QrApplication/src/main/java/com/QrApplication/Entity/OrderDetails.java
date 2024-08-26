package com.QrApplication.Entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderDetails {

	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID orderDetailsId;
	
	@Column(nullable = false)
	private String itemName;
	
	@Column(nullable = false)
	private Double amount;
	
	@Column(nullable = false)
	private Boolean isJain;
	
	@Column(nullable = false)
	private Integer quntity;
	
	private Order order;
}
