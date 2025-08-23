package com.QrApplication.Controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@GetMapping("/totalOrder/{vid}")
	public ResponseType countOrdersGroupByDay(@PathVariable("vid") String vid) {
		
		if(vid!=null) {
			return this.ordersService.countOrdersGroupByDay(UUID.fromString(vid));
		}
		else {
			return ResponseType.ResponseGenerator(RequestStatus.failure, "Invalid Request");
		}
	}
	
	@GetMapping("/custmerInsides/{vid}")
	public ResponseType custmerInsides(@PathVariable("vid") String vid) {
		
		if(vid!=null) {
			return this.ordersService.customerInsides(UUID.fromString(vid));
		}
		else {
			return ResponseType.ResponseGenerator(RequestStatus.failure, "Invalid Request");
		}
	}
	
	@GetMapping("/revenueByFoodCategory/{vid}")
	public ResponseType revenueByFoodCategory(@PathVariable("vid") String vid) {
		
		if(vid!=null) {
			return this.ordersService.revenueByFoodCategory(UUID.fromString(vid));
		}
		else {
			return ResponseType.ResponseGenerator(RequestStatus.failure, "Invalid Request");
		}
	}
	
	@GetMapping("/orderStatictics/{vid}")
	public ResponseType orderStatictics(@PathVariable("vid") String vid) {
		
		if(vid!=null) {
			return this.ordersService.orderStatictics(UUID.fromString(vid));
		}
		else {
			return ResponseType.ResponseGenerator(RequestStatus.failure, "Invalid Request");
		}
	}
	
	@GetMapping("/getTopSellingItems/{vid}")
	public ResponseType getTopSellingItems(@PathVariable("vid") String vid) {
		
		if(vid!=null) {
			return this.ordersService.getTopSellingItems(UUID.fromString(vid));
		}
		else {
			return ResponseType.ResponseGenerator(RequestStatus.failure, "Invalid Request");
		}
	}
	
	@GetMapping("/getLowestSellingItems/{vid}")
	public ResponseType getLowestSellingItems(@PathVariable("vid") String vid) {
		
		if(vid!=null) {
			return this.ordersService.getLowestSellingItems(UUID.fromString(vid));
		}
		else {
			return ResponseType.ResponseGenerator(RequestStatus.failure, "Invalid Request");
		}
	}
	

	@GetMapping("/getPaymentMethodUsed/{vid}")
	public ResponseType getPaymentMethodUsed(@PathVariable("vid") String vid) {
		
		if(vid!=null) {
			return this.ordersService.getPaymentMethodUsed(UUID.fromString(vid));
		}
		else {
			return ResponseType.ResponseGenerator(RequestStatus.failure, "Invalid Request");
		}
	}


}
