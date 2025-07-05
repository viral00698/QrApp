package com.QrApplication.Interface;

import java.util.UUID;

import com.QrApplication.Entity.OrderDetails;

public interface ApplyOffer {
	
	OrderDetails applyOffer(OrderDetails orderDetails , UUID vendorId);
}
