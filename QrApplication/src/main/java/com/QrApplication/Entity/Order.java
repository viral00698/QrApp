package com.QrApplication.Entity;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import com.QrApplication.Enum.OrderStatus;
import com.QrApplication.Enum.PaymentMode;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID order_id;
	
	private UUID customer_uuid;
	
	private String customer_mobile_no;
	
	@Column(nullable = false)
	private Long token_no;
	
	private String txid;
	
	@Column(nullable = false)
	private Set<OrderDetails> order_details;
	
	@Column(nullable = false)
	private Date orderAt;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private PaymentMode payment_mode;
	
	@Column(nullable = false)
	private UUID venderId;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;
	
	
	
	
}
