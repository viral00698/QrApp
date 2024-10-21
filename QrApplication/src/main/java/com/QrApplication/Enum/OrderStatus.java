package com.QrApplication.Enum;

public enum OrderStatus {
	PENDING,
	ONGOING,
	PREPARING,
	NOT_APPROVED, 
	PLACED,  // Order has been placed by the customer
	CANCELLED,  // Order has been cancelled
	CONFIRMED, // Order has been confirmed by the restaurant
	READY_FOR_PICKUP,
	WAIT_FOR_APPROVE,
	APPROVED,
	COMPLETE,
}	
