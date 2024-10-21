package com.QrApplication.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.QrApplication.AuthSecret.ResponseType;
import com.QrApplication.Entity.Orders;
import com.QrApplication.Enum.OrderStatus;
import com.QrApplication.Enum.RequestStatus;
import com.QrApplication.Repository.OrderRepository;

@Service
public class OrdersService {

	@Autowired
	private OrderRepository orderRepository;

	public ResponseType getWaitForAproveOrders(OrderStatus orderStatus , String vedeorId) {
		try {
			if (orderStatus == OrderStatus.WAIT_FOR_APPROVE && vedeorId !=null) {
				
				UUID vid = UUID.fromString(vedeorId);
				List<Orders> list = this.orderRepository.findByOrderStatusAndVendorId(orderStatus,vid);
				if (!list.isEmpty()) {
					return ResponseType.ResponseGenerator(RequestStatus.success, list);
				}
			}
			return ResponseType.ResponseGenerator(RequestStatus.success,
					"Currently, there are no orders available for approval.");
		} catch (Exception e) {
			return ResponseType.ResponseGenerator(RequestStatus.failure, e.getMessage());
		}
	}



	public ResponseType getOngoinOrder(UUID id) {
		
		if(!id.equals(null)) {
		
			Date date = new Date();
			List<Orders> res = this.orderRepository.getOngoinOrder(id);
			
			if(!res.isEmpty()) {
				return ResponseType.ResponseGenerator(RequestStatus.success, res);
			}else {
				return ResponseType.ResponseGenerator(RequestStatus.success, "No Conformed Order Are there");
			}
		}
		return ResponseType.ResponseGenerator(RequestStatus.failure, "Invalid Request");
	}
	
	
	public ResponseType getOrderByDateRange(List<Date> date , UUID venderId) {
		if(!date.isEmpty() && date.size() == 2  && venderId!=null) {
			Date d1 = date.get(0);
			Date d2 = date.get(1);
			
			System.err.println(date +" "+venderId);
		}
		
		return null;
	}
	
	public ResponseType findVendorOrderStatistics(Date startDate , Date endDate , UUID venderId) {
		
		try {
			if(venderId!=null && startDate!=null && endDate !=null) {
				Object obj = this.orderRepository.findVendorOrderStatistics(venderId, startDate, endDate);
				return ResponseType.ResponseGenerator(RequestStatus.success, obj);
			}	
		}catch (Exception e) {
			return ResponseType.ResponseGenerator(RequestStatus.failure, "Invalid Request");
		}
		return ResponseType.ResponseGenerator(RequestStatus.failure, "Invalid Request");
		
	}
}
