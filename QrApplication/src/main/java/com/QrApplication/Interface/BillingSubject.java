package com.QrApplication.Interface;

import com.QrApplication.Dtos.BillingDtos;
import com.QrApplication.Entity.Orders;

public interface BillingSubject {
	
	public BillingDtos billGenerator(Orders orders);

}
