package com.QrApplication.Controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.QrApplication.AuthSecret.ResponseType;
import com.QrApplication.Dtos.OrderDetailsDto;
import com.QrApplication.Entity.OrderDetails;
import com.QrApplication.Entity.Orders;
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
	
	@GetMapping("getOrdersByTokenAndVendor/{vedeorId}/{token}")
	ResponseType getbyOrdersByVendorId(@PathVariable String vedeorId , @PathVariable String token){
		return this.ordersService.getOrdersByTokenAndVendor(vedeorId , token);
	}
	
	@GetMapping("getLastTwoDayOrder/{vedeorId}")
	ResponseType getLastTwoDayOrder(@PathVariable String vedeorId){
		return this.ordersService.getLastTwoDayOrder(vedeorId);
	}
	
	@PostMapping("updateItemStatus")
	ResponseType updateItemStatus(@RequestBody OrderDetailsDto orderDetailsDto) {
		return this.ordersService.updateItemStatus(orderDetailsDto);
	}
	
	@PostMapping("qr_conform")
	ResponseType QrOrderAcceptOrPaymentConform(@RequestBody Orders orders) {
		return this.ordersService.QrOrderAcceptOrPaymentConform(orders);
	}
	
	@PostMapping("qr_close_order")
	ResponseType QrCloseOrder(@RequestBody Orders orders) {
		return this.ordersService.QrCloseOrder(orders);
	}
 
	
 

}
