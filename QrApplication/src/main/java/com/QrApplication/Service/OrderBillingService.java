package com.QrApplication.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.QrApplication.Dtos.BillingDtos;

import com.QrApplication.Entity.Orders;
import com.QrApplication.Enum.OfferType;
import com.QrApplication.Interface.BillingSubject;
import com.QrApplication.Repository.VenderRepository;

@Service
public class OrderBillingService implements BillingSubject {

	@Autowired
	private VenderRepository vendorRepository;


	@Override
	public BillingDtos billGenerator(Orders orders) {


	    BillingDtos billingDtos = new BillingDtos();
	    final double[] totalAmount = {0.0}; // Use an array to hold totalAmount

	    this.vendorRepository.findById(orders.getVendorId()).ifPresent(vendor -> {
	    	
	        // Step 1: Calculate total amount of all items
	        orders.getOrderDetails().forEach(item -> {
	        	double tmp = 0;
	        	if (item != null && OfferType.BOGO.equals(item.getOfferType())) {
	        	    tmp = item.getAmount() * item.getQuntity() / 2;
	        	} else if (item != null && OfferType.FLAT_DISCOUNT.equals(item.getOfferType())) {
	        	    tmp = item.getAmount() * item.getQuntity();
	        	}else {
	        		tmp = item.getAmount() * item.getQuntity();
	        	}
	            
	            totalAmount[0] += tmp;
	            System.err.println("Total amount of items: " + totalAmount[0]);
	        });

	        // Step 2: Calculate GST from total amount
	        double gst = (vendor.getGstCharge() / 100) * totalAmount[0];
	        gst = Double.parseDouble(String.format("%.2f", gst));
	        System.err.println("GST: " + gst);

	        // Step 3: Calculate SGST from total amount
	        double sgst = (vendor.getSgstCharge() / 100) * totalAmount[0];
	        sgst = Double.parseDouble(String.format("%.2f", sgst));
	        System.err.println("SGST: " + sgst);

	        // Step 4: Calculate restaurant charge from total amount
	        double restoCharge = (vendor.getResturentCharge() / 100) * totalAmount[0];
	        restoCharge = Double.parseDouble(String.format("%.2f", restoCharge));
	        System.err.println("Restaurant Charge: " + restoCharge);

	        // Populate BillingDtos fields
	        billingDtos.setGst(gst);
	        billingDtos.setSgst(sgst);
	        billingDtos.setResturentCharge(restoCharge);
	        billingDtos.setAmount(totalAmount[0]);

	        // Calculate final total with all charges
	        double finalTotalAmount = totalAmount[0] + gst + sgst + restoCharge;
	        finalTotalAmount = Double.parseDouble(String.format("%.2f", finalTotalAmount));
	        billingDtos.setTotalAmount(finalTotalAmount);

	    });

	    return billingDtos;

	}

}
