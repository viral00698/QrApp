package com.QrApplication.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.QrApplication.AuthSecret.ResponseType;
import com.QrApplication.Dtos.BillingDtos;
import com.QrApplication.Entity.OrderDetails;
import com.QrApplication.Entity.Orders;
import com.QrApplication.Entity.Product;
import com.QrApplication.Entity.TableOrder;
import com.QrApplication.Enum.OfferType;
import com.QrApplication.Enum.OrderStatus;
import com.QrApplication.Enum.PaymentMode;
import com.QrApplication.Enum.RequestStatus;
import com.QrApplication.Enum.TableStatus;
import com.QrApplication.Factory.OfferFactory;
import com.QrApplication.Interface.BillingSubject;
import com.QrApplication.Interface.OrderPlace;
import com.QrApplication.Interface.Payment;
import com.QrApplication.Interface.TokenGeneratorSubject;
import com.QrApplication.PreLoded.VendorMap;
import com.QrApplication.Repository.OrderDetailsRepository;
import com.QrApplication.Repository.OrderRepository;
import com.razorpay.QrCode;

@Service
public class TableOrderPlaceService implements OrderPlace {


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
	private OfferFactory offerFactory;
	
	@Autowired
	private Payment payment;

	@Autowired
	private TableOrderService tableOrderService;

	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	public ResponseType place(Orders orders) {

		try {

			TableOrder tableOrder = checkTableIsAvailable(orders);

			if (orders != null && tableOrder != null && tableOrder.getTableStatus().equals(TableStatus.AVAILABLE)) {
				tableOrder.setTableStatus(TableStatus.BOOKED);

				String prefix = vendorMap.getVenderStorenameByUUID(orders.getVendorId());
				System.err.println(prefix);

				if (prefix.isEmpty()) {
					return ResponseType.ResponseGenerator(RequestStatus.failure, "Store does not exist in the system.");
				}

				if (prefix.equals("DEF")) {
					this.vendorMap.getVenderMapFromDB();
					prefix = vendorMap.getVenderStorenameByUUID(orders.getVendorId());
				}

				String token = this.generatorToken.generatorToken(prefix);
				System.err.println("Token:" + token);
				System.err.println(PaymentMode.CASH);

				Orders savedOrder = orderDetailsSave(orders, null, token);

				if (savedOrder == null) {
					return ResponseType.ResponseGenerator(RequestStatus.failure,
							"Order is not Placed. Please try again.");
				}

				sendOrderNotificationVendor(savedOrder);
				sendOrderNotificationVendorTable(tableOrder);

				return ResponseType.ResponseGenerator(RequestStatus.success, "Order is Placed.");

			} else {
				return ResponseType.ResponseGenerator(RequestStatus.failure,
						"Table is already booked. Please choose another.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseType.ResponseGenerator(RequestStatus.failure, "Order failed. Please try again.");
		}

	}

	public TableOrder checkTableIsAvailable(Orders orders) {
		return tableOrderService.findByTableId(orders.getTableOrder().getTableId());
	}

	@Override
	public Orders orderDetailsSave(Orders orders, BillingDtos billingDtos, String token) {
		orders.setOrderStatus(OrderStatus.ONGOING);
		orders.setToken_no(token);
		orders.setTxid("00000000");
		orders.setTotelAmount(0.0);
		orders.setGst(0.0);
		orders.setSgst(0.0);
		orders.setRestaurantsCharge(0.0);
		orders.setOrderAt(new Date());

		Orders saveOrder = orderRepository.save(orders);

		List<OrderDetails> orderDetails = new ArrayList<>();
		orders.getOrderDetails().forEach(obj -> {
			obj.setOrderId(saveOrder);
			OrderDetails o = offerFactory.applyOffer(obj, saveOrder.getVendorId());
			System.err.println(o);
			
			if(OfferType.BUY_X_GET_Y.equals(obj.getOfferType()) && o!=null) {
				o.setOfferApplied(true);
				o.setOrderId(saveOrder);
				orderDetails.add(o);
			}
			
			orderDetails.add(obj);
		});

		orderDetailsRepository.saveAll(orderDetails);

		return saveOrder;
	}

	@Override
	public void orderStatusUpadte(Orders orders) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendOrderNotificationVendor(Orders orders) {

		System.err.println("order place");
		messagingTemplate.convertAndSend("/queue/"+ orders.getVendorId() +"/messages", orders);
		System.err.println("placed");
		
	}

	@Override
	public List<Product> checkAviliblity(List<UUID> list) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaymentMode checkPaymentMode(Orders orders) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendOrderNotificationVendorTable(TableOrder tableOrder) {
		messagingTemplate.convertAndSend("/queue/" + tableOrder.getVendorId() + "/tables", tableOrder);
		System.err.println("placed");

	}

	public ResponseType addItem(Orders orders) {

		try {
			if (orders != null) {

				Orders saveOrder = orderRepository.findById(orders.getOrderId()).get();
				List<OrderDetails> orderDetails = new ArrayList<>();
				orders.getOrderDetails().forEach(obj -> {
					obj.setOrderId(saveOrder);
					
					OrderDetails o = offerFactory.applyOffer(obj, saveOrder.getVendorId());
					System.err.println(o);
					
					if(OfferType.BUY_X_GET_Y.equals(obj.getOfferType()) && o!=null) {
						o.setOfferApplied(true);
						o.setOrderId(saveOrder);
						orderDetails.add(o);
					}
					
					orderDetails.add(obj);
				});
					
				List<OrderDetails> ord = orderDetailsRepository.saveAll(orderDetails);
				
				if (ord != null && !ord.isEmpty()) {
					sendOrderNotificationVendor(saveOrder);
					return ResponseType.ResponseGenerator(RequestStatus.success, "Item Added");
				}else {
					return ResponseType.ResponseGenerator(RequestStatus.failure, "Item Not Added");
				}

			}else {
				return ResponseType.ResponseGenerator(RequestStatus.failure, "Item Not Added");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseType.ResponseGenerator(RequestStatus.failure, "Item Not Added");
		}

	}

	public ResponseType invoce(Orders orders) {
		
		
		try {
			
			Orders saveOrder = orderRepository.findById(orders.getOrderId()).get();
			if(saveOrder!=null) {
				BillingDtos billingDtos = this.billingSubject.billGenerator(saveOrder);
				TableOrder tableOrder = this.checkTableIsAvailable(saveOrder);
				
				if(tableOrder !=null) {
					tableOrder.setTableStatus(TableStatus.AVAILABLE);
					this.tableOrderService.updateTableStatus(tableOrder);
				}
				
				saveOrder.setSgst(billingDtos.getSgst());
				saveOrder.setGst(billingDtos.getGst());
				saveOrder.setRestaurantsCharge(billingDtos.getResturentCharge());
				saveOrder.setOrderStatus(OrderStatus.COMPLETE);
				saveOrder.setTotelAmount(billingDtos.getTotalAmount());
				
//				System.err.println(saveOrder);
				Orders tmp = orderRepository.save(saveOrder);
				QrCode qrCode =  payment.createQr(orders);
//				tmp.setQrCode(qrCode);
				System.err.println(qrCode);
				
				if(tmp!=null) {
					tmp.setQrCode(qrCode);
					return ResponseType.ResponseGenerator(RequestStatus.success, tmp);
				}
			
			}
		}catch (Exception e) {
			return ResponseType.ResponseGenerator(RequestStatus.failure, "Invoice Not Ganreted");
		}
		
		return ResponseType.ResponseGenerator(RequestStatus.failure, "Invoice Not Ganreted");
	}
}