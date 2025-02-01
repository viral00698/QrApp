package com.QrApplication.Entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class PaymentDetail {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID pay_id;
	
	
	@Column(nullable = false)
	private String paymentId;
	
	@Column(nullable = false)
	private String orderId;
	
	@Column(nullable = false)
	private String signature;

}
