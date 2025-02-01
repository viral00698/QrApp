package com.QrApplication.Dtos;

import java.util.Set;

import com.QrApplication.Entity.OrderDetails;
import com.QrApplication.Entity.Orders;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderResponse {

	
	private BillingDtos bill;
	
	private Set<OrderDetails> orderDetails;
	
	private Orders orders;
	
	private String token;
	
	private RazorpayOrder razorpayOrder;
	
	private RazorpayResponse razorpayResponse;
}
