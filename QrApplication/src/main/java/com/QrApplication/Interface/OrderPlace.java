package com.QrApplication.Interface;

import java.util.List;
import java.util.UUID;

import com.QrApplication.Dtos.BillingDtos;
import com.QrApplication.Entity.Orders;
import com.QrApplication.Entity.Product;
import com.QrApplication.Entity.TableOrder;
import com.QrApplication.Enum.PaymentMode;

public interface OrderPlace {
	
	List<Product> checkAviliblity(List<UUID> list);
	PaymentMode checkPaymentMode(Orders orders);
	Orders orderDetailsSave(Orders orders , BillingDtos billingDtos , String token);
	void orderStatusUpadte(Orders orders);
	void sendOrderNotificationVendor(Orders orders);
	void sendOrderNotificationVendorTable(TableOrder tableOrder);
}
