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
				System.err.println("qty" + item.getQuntity());
				double tmp = item.getAmount() * item.getQuntity();
				this.totalAmount = this.totalAmount + tmp;
				System.err.println(this.totalAmount);
			});

			// step:2 calculate gst from total amount;
			double gst = (vendor.getGstCharge() / 100) * totalAmount;
			gst = Double.parseDouble(String.format("%.2f", gst));
			System.err.println(gst);
			// step:3 calculate sgst from total amount;
			double sgst = (vendor.getSgstCharge() / 100) * totalAmount;
			sgst = Double.parseDouble(String.format("%.2f", sgst));
			System.err.println(sgst);

			// step:4 calculate ResturentCharge from total amount;
			double restoCharge = (vendor.getResturentCharge() / 100) * totalAmount;
			restoCharge = Double.parseDouble(String.format("%.2f", restoCharge));
			System.err.println(restoCharge);
			// add all this amount

			billingDtos.setGst(gst);
			billingDtos.setResturentCharge(restoCharge);
			billingDtos.setSgst(sgst);
//			billingDtos.setTotalAmount(totalAmount);
			billingDtos.setAmount(totalAmount);
			totalAmount = totalAmount + gst + sgst + restoCharge;
			totalAmount = Double.parseDouble(String.format("%.2f", totalAmount));
			billingDtos.setTotalAmount(totalAmount);
			
		});

		return billingDtos;
	}

}
