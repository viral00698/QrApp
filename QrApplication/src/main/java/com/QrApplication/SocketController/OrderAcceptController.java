package com.QrApplication.SocketController;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import com.QrApplication.Entity.Orders;

@RestController
public class OrderAcceptController {
	
	private SimpMessagingTemplate simpMessagingTemplate;
	
	@MessageMapping("/orderStatus")
	public void getOrderFromVendorStatus( Orders orders) {
			System.err.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			System.err.println(orders);
			System.err.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
	}

}
