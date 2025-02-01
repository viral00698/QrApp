package com.QrApplication.Dtos;


import lombok.Data;

@Data
public class BillingDtos {
	
//	private UUID billId;
//	private Set<OrderDetails> orderDetails;
	private Double totalAmount;
	private Double amount;
	private Double gst;
	private Double sgst;
	private Double resturentCharge;
	
}
