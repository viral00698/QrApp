package com.QrApplication.Interface;


import com.QrApplication.Entity.Orders;
import com.QrApplication.Entity.TableOrder;

public interface CurrentOrderSubject {
	
	void updateOrderStatus(Orders orders);
	
	void updateTableStatus(TableOrder tableOrder);
}
