package com.QrApplication.Service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.QrApplication.AuthSecret.ResponseType;
import com.QrApplication.Entity.Orders;
import com.QrApplication.Enum.OrderStatus;
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
	public void updateOrderStatus(UUID orderId, OrderStatus orderStatus) {

		if((orderId != null || !orderId.toString().isEmpty()) && orderStatus != null){
			if(orderRepository.existsByOrderId(orderId));{
				Orders orders = new Orders();
				orders.setOrderId(orderId);
				orders.setOrderStatus(orderStatus);
				try {
				 int res = this.orderRepository.updateStatus(orderId , orderStatus);
				 if(res > 0) {
					 
					 simpMessagingTemplate.convertAndSend("/queue/a1e68d9a-4d59-4f25-a579-2bb23e928686/messages", orders);
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

}
