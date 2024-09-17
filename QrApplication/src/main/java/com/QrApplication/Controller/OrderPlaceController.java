package com.QrApplication.Controller;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.QrApplication.AuthSecret.ResponseType;
import com.QrApplication.Entity.Orders;
import com.QrApplication.Enum.RequestStatus;
import com.QrApplication.Service.OrderPlaceService;


@RestController
public class OrderPlaceController {

	@Autowired
	private OrderPlaceService orderPlaceService;
	
	@PostMapping("placeOrder")
	@Async
	public CompletableFuture<ResponseType> orderPlace(@RequestBody Orders orders) {
		return this.orderPlaceService.place(orders);	
	}
	
}
