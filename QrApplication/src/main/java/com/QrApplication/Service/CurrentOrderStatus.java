package com.QrApplication.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.QrApplication.AuthSecret.ResponseType;
import com.QrApplication.Entity.Orders;
import com.QrApplication.Entity.TableOrder;
import com.QrApplication.Interface.CurrentOrderSubject;
import com.QrApplication.Repository.OrderRepository;

@Service
public class CurrentOrderStatus implements CurrentOrderSubject{

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	
	ResponseType orderStatusResponse() {
		return null;
	}
	
	@Override
	public void updateOrderStatus(Orders order) {

		if(order.getOrderId() != null && order.getOrderStatus() != null){
			if(orderRepository.existsByOrderId(order.getOrderId()));{
				Orders orders = new Orders();
				orders.setOrderId(orders.getOrderId());
				orders.setOrderStatus(orders.getOrderStatus());
				try {
				 int res = this.orderRepository.updateStatus(order.getOrderId() , order.getOrderStatus());
				 if(res > 0) {
					 if(order.getCustomerMobileNo() != null) {
						 simpMessagingTemplate.convertAndSend("/queue/"+ order.getCustomerMobileNo() +"/messages", orders);
					 }
					 if(order.getVendorId()!= null) {
						 simpMessagingTemplate.convertAndSend("/queue/"+ order.getVendorId() +"/messages", orders);
					 }
					 
						
				 }
				}catch (Exception e) {
					System.err.println(e.getMessage());
				}
//				Orders res = this.orderRepository.save(orders);
//				System.err.println(res);
//				simpMessagingTemplate.convertAndSend("/queue/"+res.getCustomerUUID()+"/messages", res);
				
			}
		}
		
	}


	@Override
	public void updateTableStatus(TableOrder tableOrder) {

		if(tableOrder.getTableId()!=null) {
			 simpMessagingTemplate.convertAndSend("/queue/"+ tableOrder.getVendorId() +"/messages", tableOrder);
		}
	}

}
