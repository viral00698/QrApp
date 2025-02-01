package com.QrApplication.Dtos;

import lombok.Data;

@Data
public class RazorpayResponse {
		
	private String razorpay_signature;
	private String razorpay_order_id;
	private String razorpay_payment_id;
}
