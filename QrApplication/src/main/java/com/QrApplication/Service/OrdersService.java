package com.QrApplication.Service;

import java.util.List;

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

	public ResponseType getWaitForAproveOrders(OrderStatus orderStatus) {
		try {
			if (orderStatus == OrderStatus.WAIT_FOR_APPROVE) {
				List<Orders> list = this.orderRepository.findByOrderStatus(orderStatus);
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

}
