package com.QrApplication.Service;


import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.QrApplication.AuthSecret.ResponseType;
import com.QrApplication.Dtos.OrderResponse;
import com.QrApplication.Dtos.QrCodeResponseDto;
import com.QrApplication.Dtos.RazorpayOrder;
import com.QrApplication.Entity.Orders;
import com.QrApplication.Entity.PaymentDetail;
import com.QrApplication.Enum.OrderStatus;
import com.QrApplication.Enum.PaymentStatus;
import com.QrApplication.Enum.RequestStatus;
import com.QrApplication.Interface.Payment;
import com.QrApplication.Repository.OrderRepository;
import com.QrApplication.Repository.PaymentDetailRepos;
import com.razorpay.Order;
import com.razorpay.QrCode;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;

@Service
public class CreatePaymentOrder implements Payment {

	@Autowired
	private PaymentDetailRepos paymentDetailRepos;

	@Autowired
	private OrderRepository orderRepository;

	@Override
	public RazorpayOrder createOrder(Orders orders) {

		try {
			RazorpayClient razorpay = new RazorpayClient("rzp_test_gD5uJZvpUqS4ka", "pquKsUVF0RWckDL9RNzinfCH");

			JSONObject orderRequest = new JSONObject();
			System.err.println(Math.round(orders.getTotelAmount() * 100));
			orderRequest.put("amount",Math.round(orders.getTotelAmount() * 100));
			orderRequest.put("currency", "INR");
			orderRequest.put("receipt", orders.getOrderId());
//			orderRequest.put("entity", orders);
//			orderRequest.put("amount_paid", 0);
//			orderRequest.put("amount_due", orders.getTotelAmount());
//			orderRequest.put("status", "created");

			Order razorpayOrder = razorpay.orders.create(orderRequest);
			System.err.println(razorpayOrder);

			RazorpayOrder response = new RazorpayOrder();
			response.setOrderId(razorpayOrder.get("id"));
			response.setAmount(razorpayOrder.get("amount"));
			response.setCurrency(razorpayOrder.get("currency"));
			response.setStatus(razorpayOrder.get("status"));

			return response;

		} catch (RazorpayException e) {
			System.out.println(e.getMessage());
			return null;
		}

	}

	@Override
	public ResponseType verifyPaymentSignature(OrderResponse razorpayResponse) {

		try {
			// Data for verification
			JSONObject options = new JSONObject();
			options.put("razorpay_order_id", razorpayResponse.getRazorpayResponse().getRazorpay_order_id());
			options.put("razorpay_payment_id", razorpayResponse.getRazorpayResponse().getRazorpay_payment_id());
			options.put("razorpay_signature", razorpayResponse.getRazorpayResponse().getRazorpay_signature());

			boolean isValidSignature = Utils.verifyPaymentSignature(options, "pquKsUVF0RWckDL9RNzinfCH");

			if (isValidSignature) {
				System.out.println("Payment verified successfully!");

				PaymentDetail details = new PaymentDetail();
				details.setOrderId(razorpayResponse.getRazorpayResponse().getRazorpay_order_id());
				details.setPaymentId(razorpayResponse.getRazorpayResponse().getRazorpay_payment_id());
				details.setSignature(razorpayResponse.getRazorpayResponse().getRazorpay_signature());

				PaymentDetail paymentDetail = savePaymentDetail(details);
				
				if (paymentDetail != null) {
					razorpayResponse.getOrders().setTxid(paymentDetail.getOrderId());
					razorpayResponse.getOrders().setPaymentDetail(paymentDetail);
				}

				razorpayResponse.getOrders().setOrderStatus(OrderStatus.PLACED);
				razorpayResponse.getOrders().setPaymentStatus(PaymentStatus.SUCCESS);

				int res = updateOnlineOrderStatus(razorpayResponse);

				return ResponseType.ResponseGenerator(RequestStatus.success, true);
			} else {
				System.out.println("Invalid payment signature!");

				PaymentDetail details = new PaymentDetail();

				if (razorpayResponse.getRazorpayResponse().getRazorpay_order_id() != null) {
					details.setOrderId(razorpayResponse.getRazorpayResponse().getRazorpay_order_id());
					details.setPaymentId(razorpayResponse.getRazorpayResponse().getRazorpay_payment_id());
					details.setSignature(razorpayResponse.getRazorpayResponse().getRazorpay_signature());

					PaymentDetail paymentDetail = savePaymentDetail(details);
					
					System.err.println(paymentDetail);

					if (paymentDetail != null) {
						razorpayResponse.getOrders().setTxid(paymentDetail.getOrderId());
						razorpayResponse.getOrders().setPaymentDetail(paymentDetail);
					}
				}
				razorpayResponse.getOrders().setOrderStatus(OrderStatus.FAIELD);
				razorpayResponse.getOrders().setPaymentStatus(PaymentStatus.FAIELD);

				int res =  updateOnlineOrderStatus(razorpayResponse);

				return ResponseType.ResponseGenerator(RequestStatus.success, false);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return ResponseType.ResponseGenerator(RequestStatus.failure, false);
		}

	}
	
	
	public int updateOnlineOrderStatus(OrderResponse orderResponse) {
		
		if(orderResponse!=null) {
			try {
				int res = orderRepository.updateOnlineOrderStatus(orderResponse.getOrders().getOrderId(),
																  orderResponse.getOrders().getOrderStatus(),
																  orderResponse.getRazorpayResponse().getRazorpay_payment_id(),
																  orderResponse.getOrders().getPaymentStatus(),
																  orderResponse.getOrders().getPaymentDetail()
						);
				return res;
			} catch (Exception e) {
				return -1;
			}
			
		}
		return 0;
	}
	
	public PaymentDetail savePaymentDetail(PaymentDetail paymentDetail) {
		return paymentDetailRepos.save(paymentDetail);
	}

	@Override
	public QrCode createQr(Orders orders) {
		
		RazorpayClient razorpay;
		try {
		razorpay = new RazorpayClient("rzp_test_gD5uJZvpUqS4ka", "pquKsUVF0RWckDL9RNzinfCH");
	
		JSONObject qrRequest = new JSONObject();
		qrRequest.put("type","upi_qr");
		qrRequest.put("name",orders.getRestroName());
		qrRequest.put("usage","single_use");
		qrRequest.put("fixed_amount",true);
		qrRequest.put("payment_amount",Math.round(orders.getTotelAmount() * 100));
//		qrRequest.put("customer_id",orders.getOrderId());
		
		Long expireTime = (System.currentTimeMillis() / 1000) + (10 * 60); // Convert to seconds

		qrRequest.put("close_by",expireTime);
		
		QrCode qrcode = razorpay.qrCode.create(qrRequest);
	
		return qrcode;
		} catch (RazorpayException e) {
			return null;
		}
		
	}

}
