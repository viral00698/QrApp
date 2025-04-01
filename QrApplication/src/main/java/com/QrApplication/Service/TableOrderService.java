package com.QrApplication.Service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.QrApplication.AuthSecret.ResponseType;
import com.QrApplication.Dtos.BillingDtos;
import com.QrApplication.Dtos.OrderResponse;
import com.QrApplication.Dtos.RazorpayOrder;
import com.QrApplication.Entity.Orders;
import com.QrApplication.Entity.PaymentDetail;
import com.QrApplication.Entity.TableOrder;
import com.QrApplication.Enum.OrderStatus;
import com.QrApplication.Enum.RequestStatus;
import com.QrApplication.Interface.BillingSubject;
import com.QrApplication.Repository.OrderRepository;
import com.QrApplication.Repository.PaymentDetailRepos;
import com.QrApplication.Repository.TableOrderRepository;

@Service
public class TableOrderService {

	@Autowired
	private TableOrderRepository tableOrderRepository;
	
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private BillingSubject billingSubject;
	
	@Autowired
	private PaymentDetailRepos paymentDetailRepos;
	
	@Autowired
	private CreatePaymentOrder createPaymentOrder;
	
	public ResponseType addTable(TableOrder tableOrder) {
		
		if(tableOrder.getTableName()!=null && tableOrder.getVendorId()!=null) {
			TableOrder t = this.tableOrderRepository.save(tableOrder);
			System.err.println(t);
			if(t!=null) {
				return ResponseType.ResponseGenerator(RequestStatus.success, "Exciting Update: New Table Added!");
			}
		}
		return ResponseType.ResponseGenerator(RequestStatus.failure, "Invalid Request");
	}
	
	public ResponseType getTableByVendorId(UUID vendorId) {
		
		if(vendorId!=null) {
			List<TableOrder> t = this.tableOrderRepository.findByVendorId(vendorId);
			if(t!=null) {
				return ResponseType.ResponseGenerator(RequestStatus.success, t);
			}
		}
		return ResponseType.ResponseGenerator(RequestStatus.failure, "Invalid Request");
	}

	public ResponseType deleteTableByVendorId(UUID tableId) {
		if(tableId!=null) {
			int t = this.tableOrderRepository.deleteTableByVendorId(tableId);
			if(t>0) {
				return ResponseType.ResponseGenerator(RequestStatus.success, "Exciting Update: Table Deleted!");
			}
		}
		return ResponseType.ResponseGenerator(RequestStatus.failure, "Invalid Request");
	}
	
	public ResponseType updateTableStatus(TableOrder tableOrder) {
		if(tableOrder!=null && tableOrder.getTableId()!=null && tableOrder.getTableStatus()!=null && tableOrder.getVendorId()!=null) {
			int t = this.tableOrderRepository.updateTableStatus(tableOrder.getTableStatus() , tableOrder.getTableId(), tableOrder.getVendorId());
			if(t>0) {
				return ResponseType.ResponseGenerator(RequestStatus.success, "Table Status Updated Successfully!");
			}
		}
		return ResponseType.ResponseGenerator(RequestStatus.failure, "Invalid Request");
	}
	
	public TableOrder findByTableId(UUID tableId) {
		return tableOrderRepository.findByTableId(tableId);
	}

	public PaymentDetail savePaymentDetail(PaymentDetail paymentDetail) {
		return paymentDetailRepos.save(paymentDetail);
	}
	
	public ResponseType createRozerpayOrderForTable(Orders reqOrder) {
		
//		OrderResponse orderResponse = new OrderResponse();
		
		try {
			
			Orders orders = this.orderRepository.getOrdersByOrderIdAndVendorId(reqOrder.getVendorId() , reqOrder.getOrderId());
			BillingDtos billingDtos = this.billingSubject.billGenerator(orders);
			orders.setOrderStatus(OrderStatus.COMPLETE);
			
			orders.setGst(billingDtos.getGst());
			orders.setSgst(billingDtos.getSgst());
			orders.setRestaurantsCharge(billingDtos.getResturentCharge());
			orders.setTotelAmount(billingDtos.getTotalAmount());
			
			PaymentDetail paymentDetail = new PaymentDetail();
			paymentDetail.setOrderId(reqOrder.getOrderId().toString());
			paymentDetail.setPaymentId(reqOrder.getTxnNo());
			paymentDetail.setSignature(reqOrder.getRefNo());
			
			savePaymentDetail(paymentDetail);
			
			this.orderRepository.save(orders);
			
			
//			RazorpayOrder obj =  createPaymentOrder.createOrder(orders); // razorpay order
//			orderResponse.setRazorpayOrder(obj);
//			orderResponse.setOrders(orders);
			
			return ResponseType.ResponseGenerator(RequestStatus.success, "Payment Details added");
			
		} catch (Exception e) {
			System.err.println("Getting error while createing order for razorpay"+ e.getMessage());
			return ResponseType.ResponseGenerator(RequestStatus.success , "Getting error while createing order for razorpay");
		}
	}
	
	
	
	

}
