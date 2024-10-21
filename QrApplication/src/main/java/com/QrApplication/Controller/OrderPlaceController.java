package com.QrApplication.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.QrApplication.AuthSecret.ResponseType;
import com.QrApplication.Entity.Orders;
import com.QrApplication.Service.OrderPlaceService;


@RestController
@RequestMapping("api/v1/qr/order")
public class OrderPlaceController {

	@Autowired
	private OrderPlaceService orderPlaceService;
	
	@PostMapping("placeOrder")
	public ResponseType orderPlace(@RequestBody Orders orders) {

		return this.orderPlaceService.place(orders);	
	}
	
}
