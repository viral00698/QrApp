package com.QrApplication.Controller;

import java.util.UUID;

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
	
	@GetMapping("getbyStatus/{status}/{vedeorId}")
	ResponseType getWaitForAproveOrder(@PathVariable OrderStatus status  , @PathVariable String vedeorId){
		return this.ordersService.getWaitForAproveOrders(status , vedeorId);
	}
	
	@GetMapping("getOngoingOrder/{id}")
	ResponseType getWaitForAproveOrder(@PathVariable String id){
		return this.ordersService.getOngoinOrder(UUID.fromString(id));
	}
	
	
	@GetMapping("getbyTableOrder/{vedeorId}/{tableId}")
	ResponseType getTableOnGoingOrder(@PathVariable String vedeorId , @PathVariable String tableId){
		return this.ordersService.getTableOnGoingOrder(vedeorId , tableId);
	}
	
	@GetMapping("getbyOrdersByVendorId/{vedeorId}")
	ResponseType getbyOrdersByVendorId(@PathVariable String vedeorId){
		return this.ordersService.getbyOrdersByVendorId(vedeorId);
	}
 
	
 

}
