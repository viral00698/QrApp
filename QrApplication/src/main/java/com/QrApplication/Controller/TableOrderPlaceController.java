package com.QrApplication.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.QrApplication.AuthSecret.ResponseType;
import com.QrApplication.Entity.Orders;
import com.QrApplication.Service.TableOrderPlaceService;

@RestController
@RequestMapping("api/v1/tableOrder/")
public class TableOrderPlaceController {

	@Autowired
	private TableOrderPlaceService tableOrderPlaceService;
	
	@PostMapping("place")
	public ResponseType tableOrderPlace(@RequestBody Orders orders) {
		
		if(orders.getOrderId()==null) {
			return tableOrderPlaceService.place(orders);
		}else {
			System.err.println("ok");
			return tableOrderPlaceService.addItem(orders);
		}
		
	}
	
	@PostMapping("invoce")
	public ResponseType invoce(@RequestBody Orders orders) {
		return this.tableOrderPlaceService.invoce(orders);
	}
	
	
}
