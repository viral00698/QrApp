package com.QrApplication.Interface;

import com.QrApplication.AuthSecret.ResponseType;
import com.QrApplication.Dtos.OrderResponse;
import com.QrApplication.Dtos.RazorpayOrder;
import com.QrApplication.Entity.Orders;

public interface Payment {
	
	RazorpayOrder createOrder(Orders orders);
	ResponseType verifyPaymentSignature(OrderResponse razorpayResponse);
	
}
