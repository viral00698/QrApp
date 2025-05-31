package com.QrApplication.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.QrApplication.AuthSecret.ResponseType;
import com.QrApplication.Dtos.BillingDtos;
import com.QrApplication.Dtos.StatisticsDto;
import com.QrApplication.Entity.Orders;
import com.QrApplication.Enum.OrderStatus;
import com.QrApplication.Enum.RequestStatus;
import com.QrApplication.Interface.BillingSubject;
import com.QrApplication.Repository.OrderDetailsRepository;
import com.QrApplication.Repository.OrderRepository;

@Service
public class OrdersService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private BillingSubject billingSubject;
	
	@Autowired
	private OrderDetailsRepository orderDetailsRepository;

	public ResponseType getWaitForAproveOrders(OrderStatus orderStatus, String vedeorId) {
		try {
			if (orderStatus == OrderStatus.WAIT_FOR_APPROVE && vedeorId != null) {

				UUID vid = UUID.fromString(vedeorId);
				List<Orders> list = this.orderRepository.findByOrderStatusAndVendorId(orderStatus, vid);
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

		if (!id.equals(null)) {

//			Date date = new Date();
			List<Orders> res = this.orderRepository.getOngoinOrder(id);

			if (!res.isEmpty()) {
				return ResponseType.ResponseGenerator(RequestStatus.success, res);
			} else {
				return ResponseType.ResponseGenerator(RequestStatus.success, "No Conformed Order Are there");
			}
		}
		return ResponseType.ResponseGenerator(RequestStatus.failure, "Invalid Request");
	}

	public ResponseType getOrderByDateRange(List<Date> date, UUID venderId) {
		if (!date.isEmpty() && date.size() == 2 && venderId != null) {
//			Date d1 = date.get(0);
//			Date d2 = date.get(1);

			System.err.println(date + " " + venderId);
		}

		return null;
	}

	public ResponseType findVendorOrderStatistics(Date startDate, Date endDate, UUID venderId) {

		try {
			if (venderId != null && startDate != null && endDate != null) {
				Object obj = this.orderRepository.findVendorOrderStatistics(venderId, startDate, endDate);
				return ResponseType.ResponseGenerator(RequestStatus.success, obj);
			}
		} catch (Exception e) {
			return ResponseType.ResponseGenerator(RequestStatus.failure, "Invalid Request");
		}
		return ResponseType.ResponseGenerator(RequestStatus.failure, "Invalid Request");

	}

	public ResponseType getTableOnGoingOrder(String vedeorId, String tableId) {

		try {
			if (vedeorId != null && tableId != null) {

				UUID vid = UUID.fromString(vedeorId);
				UUID tid = UUID.fromString(tableId);

				List<Orders> list = this.orderRepository.getTableOnGoingOrder(tid, vid);
			
				if(list.isEmpty()) {
					return ResponseType.ResponseGenerator(RequestStatus.success,
							"Currently, there are no orders available for approval.");
				}
				Orders orders = list.get(0);
				BillingDtos billingDtos = this.billingSubject.billGenerator(orders);
				orders.setBillingDtos(billingDtos);
				
				if (orders!=null) {
					return ResponseType.ResponseGenerator(RequestStatus.success, orders);
				}
			
			}
			return ResponseType.ResponseGenerator(RequestStatus.success,
					"Currently, there are no orders available for approval.");
		} catch (Exception e) {
			return ResponseType.ResponseGenerator(RequestStatus.failure, e.getMessage());

		}
	}

	public ResponseType getbyOrdersByVendorId(String vedeorId) {

		try {
			List<Orders> res = this.orderRepository.findByVendorId(UUID.fromString(vedeorId));
			return ResponseType.ResponseGenerator(RequestStatus.success, res);
		} catch (Exception e) {
			return ResponseType.ResponseGenerator(RequestStatus.failure, "Request Getting Error");
		}

	}

	public ResponseType findTop10MostOrderedItemsByVendorAndDateRange(StatisticsDto statisticsDto) {

		try {
			Pageable topTen = PageRequest.of(0, 10);
			List<Object> mostOrderedItems = orderDetailsRepository.findTop10MostOrderedItemsByVendorAndDateRange(
					statisticsDto.getVenderId(), statisticsDto.getStartDate(), statisticsDto.getEndDate(), topTen);
			return ResponseType.ResponseGenerator(RequestStatus.success, mostOrderedItems);
		} catch (Exception e) {
			return ResponseType.ResponseGenerator(RequestStatus.failure,
					"Getting Error While findTop10MostOrderedItemsByVendorAndDateRange");
		}

	}

	public ResponseType getOrdersByTokenAndVendor(String vedeorId, String token) {
		try {
			if (!vedeorId.isBlank() && !token.isBlank()) {
				Orders orders = this.orderRepository.getOrdersByTokenAndVendor(UUID.fromString(vedeorId), token);

				return ResponseType.ResponseGenerator(RequestStatus.success, orders);
			} else {
				return ResponseType.ResponseGenerator(RequestStatus.failure, "Given parameter is invalid");
			}
		} catch (Exception e) {
			return ResponseType.ResponseGenerator(RequestStatus.failure, "Geeting error while getRecord by token");
		}

	}

	public ResponseType getLastTwoDayOrder(String vedeorId) {
		
		try {
			if (!vedeorId.isBlank()) {
				LocalDateTime last24Hours = LocalDateTime.now().minusHours(24);
				List<Orders>  orders = this.orderRepository.getLastTwoDayOrder(UUID.fromString(vedeorId) , last24Hours);
				
				return ResponseType.ResponseGenerator(RequestStatus.success, orders);
			} else {
				return ResponseType.ResponseGenerator(RequestStatus.failure, "Given parameter is invalid");
			}
		} catch (Exception e) {
			return ResponseType.ResponseGenerator(RequestStatus.failure, "Geeting error while getLastTwoDayOrder by vedeorId");
		}
	}

	public ResponseType countOrdersGroupByDay(UUID vid) {
		try {
			List<Object[]> obj = this.orderRepository.countOrdersGroupByDay(vid);
			return ResponseType.ResponseGenerator(RequestStatus.success, obj);
		} catch (Exception e) {
			return ResponseType.ResponseGenerator(RequestStatus.failure, "Geeting error while fatching order by vedeorId");
		}
	}
	
	public ResponseType customerInsides(UUID vid) {
		try {
			List<Object[]> obj = this.orderRepository.customerInsides(vid);
			return ResponseType.ResponseGenerator(RequestStatus.success, obj);
		} catch (Exception e) {
			return ResponseType.ResponseGenerator(RequestStatus.failure, "Geeting error while fatching customerInsides by vedeorId");
		}
	}

}
