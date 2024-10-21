package com.QrApplication.SocketController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import com.QrApplication.Entity.Orders;
import com.QrApplication.Interface.CurrentOrderSubject;
import com.QrApplication.Service.CurrentOrderStatus;

@RestController
public class OrderAcceptController {
	
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	@Autowired
	private CurrentOrderSubject currentOrderStatus;  
	
	@MessageMapping("/orderStatus")
	public void getOrderStatusFromVender( Orders orders) {
			System.err.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			System.err.println(orders);
			System.err.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			System.err.println(orders.getCustomerUUID());
		this.simpMessagingTemplate.convertAndSend("/queue/"+ orders.getCustomerUUID() +"/messages" , orders);
		this.currentOrderStatus.updateOrderStatus(orders.getOrderId(),orders.getOrderStatus());
	}

}
