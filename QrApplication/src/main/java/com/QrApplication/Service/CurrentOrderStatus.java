package com.QrApplication.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.QrApplication.AuthSecret.ResponseType;
import com.QrApplication.Entity.Orders;
import com.QrApplication.Entity.TableOrder;
import com.QrApplication.Enum.OrderStatus;
import com.QrApplication.Enum.PaymentMode;
import com.QrApplication.Enum.PaymentStatus;
import com.QrApplication.Interface.CurrentOrderSubject;
import com.QrApplication.Repository.OrderRepository;
import com.twilio.rest.media.v1.MediaProcessor.Order;

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
				orders.setPaymentStatus(order.getPaymentStatus());
				try {
				 int res = this.orderRepository.updateStatus(order.getOrderId() , order.getOrderStatus());
				 
				 
				 
				 if(order.getPayment_mode() == PaymentMode.CASH) {
					 int r = this.orderRepository.updatePaymentStatus(order.getOrderId(), order.getPaymentStatus());
				 }
				
				 if(res > 0) {
					 Orders od = orderRepository.findById(order.getOrderId()).get();
					 if(od == null)
						 return;
					 if(order.getCustomerMobileNo() != null) {
						 simpMessagingTemplate.convertAndSend("/queue/"+ order.getCustomerMobileNo() +"/messages", od);
					 }
					 if(order.getVendorId()!= null) {
						 simpMessagingTemplate.convertAndSend("/queue/"+ order.getVendorId() +"/messages", od);
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
			 simpMessagingTemplate.convertAndSend("/queue/table"+ tableOrder.getVendorId() +"/messages", tableOrder);
		}
	}

}
