package com.QrApplication.Dtos;

import java.util.Set;
import java.util.UUID;

import com.QrApplication.Entity.OrderDetails;

import lombok.Data;

@Data
public class BillingDtos {
	
//	private UUID billId;
//	private Set<OrderDetails> orderDetails;
	private Double totalAmount;
	private Double amountl;
	private Double gst;
	private Double sgst;
	private Double resturentCharge;
	
}
