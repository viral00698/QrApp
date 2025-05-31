package com.QrApplication.Interface;


import com.QrApplication.AuthSecret.ResponseType;
import com.QrApplication.Dtos.OrderResponse;
import com.QrApplication.Dtos.RazorpayOrder;
import com.QrApplication.Entity.Orders;
import com.razorpay.QrCode;

public interface Payment {
	
	RazorpayOrder createOrder(Orders orders);
	ResponseType verifyPaymentSignature(OrderResponse razorpayResponse);
	QrCode createQr(Orders orders);
	
}
