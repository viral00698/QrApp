package com.QrApplication.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.QrApplication.AuthSecret.ResponseType;
import com.QrApplication.Dtos.BillingDtos;
import com.QrApplication.Dtos.OrderDetailsDto;
import com.QrApplication.Dtos.OrderHistoryDto;
import com.QrApplication.Dtos.StatisticsDto;
import com.QrApplication.Entity.OrderDetails;
import com.QrApplication.Entity.Orders;
import com.QrApplication.Entity.Product;
import com.QrApplication.Entity.Vendor;
import com.QrApplication.Enum.OrderStatus;
import com.QrApplication.Enum.RequestStatus;
import com.QrApplication.Interface.BillingSubject;
import com.QrApplication.Interface.CurrentOrderSubject;
import com.QrApplication.Repository.OrderDetailsRepository;
import com.QrApplication.Repository.OrderRepository;
import com.QrApplication.Repository.ProductRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Service
public class OrdersService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private BillingSubject billingSubject;
	
	@Autowired
	private OrderDetailsRepository orderDetailsRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CurrentOrderSubject currentOrderSubject;

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
		
		try {
			
			if (!id.equals(null)) {

//				Date date = new Date();
				List<Orders> res = this.orderRepository.getOngoinOrder(id);
				
				List<Orders> newList = new ArrayList<>();
				for(Orders orders : res) {
					BillingDtos billingDtos = this.billingSubject.billGenerator(orders);
					orders.setBillingDtos(billingDtos);
					newList.add(orders);
				}

				if (!res.isEmpty()) {
					return ResponseType.ResponseGenerator(RequestStatus.success, newList);
				} else {
					return ResponseType.ResponseGenerator(RequestStatus.success, "No Conformed Order Are there");
				}
			}
			return ResponseType.ResponseGenerator(RequestStatus.failure, "Invalid Request");
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
			return ResponseType.ResponseGenerator(RequestStatus.failure,e.getMessage());
		}

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
			
			List<Product> product = productRepository.getProduct(UUID.fromString(vedeorId));
			
			Map<UUID, String> map = new HashMap<>();
		
			for(Product p : product) {
				map.put(p.getProductId(), p.getItemName());
			}
			
			OrderHistoryDto orderHistoryDto = new OrderHistoryDto();
			orderHistoryDto.setOrders(res);
			orderHistoryDto.setProduct(map);
			
			return ResponseType.ResponseGenerator(RequestStatus.success, orderHistoryDto);
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
				
				BillingDtos bill =  billingSubject.billGenerator(orders);
				orders.setBillingDtos(bill);
				System.err.println(bill);
System.err.println(orders);
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
	
	public ResponseType revenueByFoodCategory(UUID vid) {
		try {
			List<Object[]> obj = this.orderRepository.revenueByFoodCategory(vid);
			return ResponseType.ResponseGenerator(RequestStatus.success, obj);
		} catch (Exception e) {
			return ResponseType.ResponseGenerator(RequestStatus.failure, "Geeting error while fatching revenueByFoodCategory by vedeorId");
		}
	}
	
	public ResponseType orderStatictics(UUID vid) {
		try {
			List<Object[]> obj = this.orderRepository.orderStatictics(vid);
			return ResponseType.ResponseGenerator(RequestStatus.success, obj);
		} catch (Exception e) {
			return ResponseType.ResponseGenerator(RequestStatus.failure, "Geeting error while fatching orderStatictics by vedeorId");
		}
	}
	
	public ResponseType getLowestSellingItems(UUID vid) {
		try {
			List<Object[]> obj = this.orderRepository.getLowestSellingItems(vid);
			return ResponseType.ResponseGenerator(RequestStatus.success, obj);
		} catch (Exception e) {
			return ResponseType.ResponseGenerator(RequestStatus.failure, "Geeting error while fatching getLowestSellingItems by vedeorId");
		}
	}
	
	public ResponseType getTopSellingItems(UUID vid) {
		try {
			List<Object[]> obj = this.orderRepository.getTopSellingItems(vid);
			return ResponseType.ResponseGenerator(RequestStatus.success, obj);
		} catch (Exception e) {
			return ResponseType.ResponseGenerator(RequestStatus.failure, "Geeting error while fatching getTopSellingItems by vedeorId");
		}
	}

	public ResponseType getPaymentMethodUsed(UUID vid) {
		
		try {
			List<Orders> obj = this.orderRepository.getPaymentMethodUsed(vid);
			return ResponseType.ResponseGenerator(RequestStatus.success, obj);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseType.ResponseGenerator(RequestStatus.failure, "Geeting error while fatching getPaymentMethodUsed by vedeorId");
		}
	}

	public ResponseType updateItemStatus(OrderDetailsDto orderDetailsDto) {
	
	    try {
	        int updatedCount = orderDetailsRepository.updateItemStatus(
	            orderDetailsDto.getOrderDetailsId(), 
	            orderDetailsDto.getIsDelivered()
	        );

	        if (updatedCount > 0) {
	        	Boolean res = orderDetailsDto.getIsDelivered();
	            return ResponseType.ResponseGenerator(RequestStatus.success, "Item delivery status updated successfully." , res);
	        } else {
	            return ResponseType.ResponseGenerator(RequestStatus.failure, "No item found to update.");
	        }

	    } catch (Exception e) {
	        return ResponseType.ResponseGenerator(RequestStatus.failure, "Error occurred while updating item status: " + e.getMessage());
	    }
	}

	public ResponseType QrOrderAcceptOrPaymentConform(Orders orders) {
		 try {
		        this.currentOrderSubject.updateOrderStatus(orders);
		        return ResponseType.ResponseGenerator(RequestStatus.success, "");
		    } catch (Exception e) {
		        return ResponseType.ResponseGenerator(RequestStatus.failure, "Error occurred while updating item status: " + e.getMessage());
		    }
	}

	public ResponseType QrCloseOrder(Orders orders) {
		
		 try {	
			
			 	if(orders!=null && orders.getOrderId()!=null && OrderStatus.COMPLETE == orders.getOrderStatus()) {
			 		int x = this.orderRepository.updateStatus(orders.getOrderId(), OrderStatus.COMPLETE);
			 		
			 		if(x > 0) {
			 			 this.currentOrderSubject.updateOrderStatus(orders);
					     return ResponseType.ResponseGenerator(RequestStatus.success, "Order Close");
			 		}
			 	}
			 
			 	 return ResponseType.ResponseGenerator(RequestStatus.failure, "getting Error while close order");
		    } catch (Exception e) {
		        return ResponseType.ResponseGenerator(RequestStatus.failure, "Error occurred while close item status: " + e.getMessage());
		    }
	}



}
