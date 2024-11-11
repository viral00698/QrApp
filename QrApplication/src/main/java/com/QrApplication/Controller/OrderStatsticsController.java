package com.QrApplication.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.QrApplication.AuthSecret.ResponseType;
import com.QrApplication.Dtos.StatisticsDto;
import com.QrApplication.Enum.RequestStatus;
import com.QrApplication.Service.OrdersService;

@RestController
@RequestMapping("OrderStatstics")
public class OrderStatsticsController {

	@Autowired
	private OrdersService ordersService;
	
	@PostMapping("/daily")
	public ResponseType getDailyStatistics(@RequestBody StatisticsDto object) {
//		System.err.println(object);
		if(object.getEndDate()!=null && object.getStartDate()!=null && object.getVenderId()!=null) {
			return this.ordersService.findVendorOrderStatistics(object.getStartDate(), object.getEndDate(), object.getVenderId());
		}else {
			return ResponseType.ResponseGenerator(RequestStatus.success, "Invalid Request");
		}
		
	}
	
	@PostMapping("/findTop10MostOrderedItems")
	public ResponseType findTop10MostOrderedItemsByVendorAndDateRange(@RequestBody StatisticsDto statisticsDto) {
		
		if(statisticsDto!=null) {
			return this.ordersService.findTop10MostOrderedItemsByVendorAndDateRange(statisticsDto);
		}
		else {
			return ResponseType.ResponseGenerator(RequestStatus.failure, "Invalid Request");
		}
	}	
}
