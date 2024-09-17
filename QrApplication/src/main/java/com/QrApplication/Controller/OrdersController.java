package com.QrApplication.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.QrApplication.AuthSecret.ResponseType;
import com.QrApplication.Enum.OrderStatus;
import com.QrApplication.Service.OrdersService;

@RestController
@RequestMapping("Orders")
public class OrdersController {
	
	@Autowired
	private OrdersService ordersService;
	
	@GetMapping("getbyStatus/{status}")
	ResponseType getWaitForAproveOrder(@PathVariable OrderStatus status ){
	
		return this.ordersService.getWaitForAproveOrders(status);
	}
}
