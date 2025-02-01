package com.QrApplication.Interface;

import java.util.UUID;

import com.QrApplication.Enum.OrderStatus;

public interface CurrentOrderSubject {
	
	void updateOrderStatus(UUID orderId , OrderStatus orderStatus);
}
