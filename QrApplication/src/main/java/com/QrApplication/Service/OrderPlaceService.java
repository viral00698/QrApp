package com.QrApplication.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.QrApplication.AuthSecret.ResponseType;
import com.QrApplication.Dtos.BillingDtos;
import com.QrApplication.Entity.OrderDetails;
import com.QrApplication.Entity.Orders;
import com.QrApplication.Entity.Product;
import com.QrApplication.Enum.OrderStatus;
import com.QrApplication.Enum.PaymentMode;
import com.QrApplication.Enum.RequestStatus;
import com.QrApplication.Interface.BillingSubject;
import com.QrApplication.Interface.OrderPlace;
import com.QrApplication.Interface.TokenGeneratorSubject;
import com.QrApplication.PreLoded.VendorMap;
import com.QrApplication.Repository.OrderDetailsRepository;
import com.QrApplication.Repository.OrderRepository;
import com.QrApplication.Repository.ProductRepository;

@Service
public class OrderPlaceService implements OrderPlace {

	private final Logger logger = LoggerFactory.getLogger(OrderPlaceService.class);

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private BillingSubject billingSubject;

	@Autowired
	private TokenGeneratorSubject generatorToken;

	@Autowired
	private OrderDetailsRepository orderDetailsRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private VendorMap vendorMap;

	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	/**
	 * @param orders
	 * @return
	 */
	public CompletableFuture<ResponseType> place(Orders order) {

		
		return CompletableFuture.supplyAsync(()->{
			
			// step:1 check order aviliblity in database
			Orders orders = Optional.ofNullable(order).get();
			List<UUID> list = orders.getOrderDetails().stream().map(OrderDetails::getProductId)
					.collect(Collectors.toList());
			List<Product> products = this.checkAviliblity(list);
			System.err.println(products);
			if (products.isEmpty()) {
				System.err.println("null values");
				return null;
			}

			// step:2 make bill here
			BillingDtos billingDtos = this.billingSubject.billGenerator(orders);

//			 step:3 make payment if CASH;
			if (this.checkPaymentMode(orders).equals(PaymentMode.CASH)) {

				String prefix = vendorMap.getVenderStorenameByUUID(orders.getVendorId());
				System.err.println(prefix);
				if (prefix.equals("DEF")) {
					this.vendorMap.getVenderMapFromDB();
					prefix = vendorMap.getVenderStorenameByUUID(orders.getVendorId());
				}
				String token = this.generatorToken.generatorToken(prefix);
				System.err.println("Token:"+ token);

				System.err.println(PaymentMode.CASH);

				// step:3.1 save order details in database
				Orders savedOrder = this.orderDetailsSave(orders, billingDtos, token);

				// step:3.2 send request to the vender for order accept;
				// send notification to the vendor
				this.sendOrderNotificationVendor(orders);

			}

			// step:3 make payment if ONLINE;
//			if (this.checkPaymentMode(orders).equals(PaymentMode.ONLINE)) {
	//
//			}
			
			return ResponseType.ResponseGenerator(RequestStatus.success , "Order Placed");
			
		}).exceptionally(ex->{
			System.err.println(ex.getMessage());
			 return ResponseType.ResponseGenerator(RequestStatus.failure, "Error placing order");
		});
		
	

	}

	@Override
	public List<Product> checkAviliblity(List<UUID> list) {

		List<Product> products = this.productRepository.checkAviliblityInDb(list);
		if (products != null) {
			return products;
		}
		logger.warn("No products returned from the database for the given IDs.");
		return null;
	}

	@Override
	public PaymentMode checkPaymentMode(Orders obj) {
		return obj.getPayment_mode();
	}

	@Override
	public Orders orderDetailsSave(Orders orders, BillingDtos billingDtos, String token) {

		orders.setOrderStatus(OrderStatus.WAIT_FOR_APPROVE);
		orders.setToken_no(token);
		orders.setTxid("00000000");
		orders.setTotelAmount(billingDtos.getTotalAmount());
		orders.setGst(billingDtos.getGst());
		orders.setSgst(billingDtos.getSgst());
		orders.setRestaurantsCharge(billingDtos.getResturentCharge());
	    orders.setOrderAt(new Date());
		Orders saveOrder = orderRepository.save(orders);

		List<OrderDetails> orderDetails = new ArrayList<>();
		orders.getOrderDetails().forEach(obj -> {
			obj.setOrderId(saveOrder);
			orderDetails.add(obj);
		});

		orderDetailsRepository.saveAll(orderDetails);

		return saveOrder;

	}

	@Override
	public void orderStatusUpadte(Orders orders) {
//		messagingTemplate.convertAndSend("/queue/test", orders);
	}

	@Override
	public void sendOrderNotificationVendor(Orders orders) {
		String user="viral";
//		messagingTemplate.convertAndSend("/app/sendMessage", orders);
		messagingTemplate.convertAndSend("/queue/viral/messages", orders);
//		messagingTemplate.convertAndSendToUser(user, "/queue/messages", orders);
		System.err.println("placed");

	}

}
