package com.QrApplication.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.QrApplication.Dtos.BillingDtos;

import com.QrApplication.Entity.Orders;
import com.QrApplication.Interface.BillingSubject;
import com.QrApplication.Repository.VenderRepository;

@Service
public class OrderBillingService implements BillingSubject {

	@Autowired
	private VenderRepository vendorRepository;

	private double totalAmount;

	@Override
	public BillingDtos billGenerator(Orders orders) {

		BillingDtos billingDtos = new BillingDtos();
		
		this.vendorRepository.findById(orders.getVendorId()).ifPresent(vendor -> {

			// step:1 totalAmount of all items

			orders.getOrderDetails().stream().forEach(item -> {
				double tmp = item.getAmount() * item.getQuntity();
				this.totalAmount = this.totalAmount + tmp;
			});

			// step:2 calculate gst from total amount;
			double gst = (vendor.getGstCharge() / 100) * totalAmount;

			// step:3 calculate sgst from total amount;
			double sgst = (vendor.getSgstCharge() / 100) * totalAmount;

			// step:4 calculate ResturentCharge from total amount;
			double restoCharge = (vendor.getResturentCharge() / 100) * totalAmount;

			// add all this amount

			billingDtos.setGst(gst);
			billingDtos.setResturentCharge(restoCharge);
			billingDtos.setSgst(sgst);
			billingDtos.setTotalAmount(totalAmount);
			billingDtos.setAmountl(totalAmount);
			totalAmount = gst + sgst + restoCharge;
		
		});

		return billingDtos;
	}

}
