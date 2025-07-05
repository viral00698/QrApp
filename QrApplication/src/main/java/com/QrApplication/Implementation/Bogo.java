package com.QrApplication.Implementation;

import java.util.HashMap;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.QrApplication.Entity.Offer;
import com.QrApplication.Entity.OrderDetails;
import com.QrApplication.Interface.ApplyOffer;
import com.QrApplication.PreLoded.VendorOffer;

@Component
public class Bogo implements ApplyOffer {

	@Autowired
	private VendorOffer vendorOffer;

	HashMap<UUID, Offer> offerMap;

	public Bogo() {
		this.offerMap = new HashMap<>();
	}

	@Override
	public OrderDetails applyOffer(OrderDetails orderDetails, UUID vendorId) {

		
		try {
			
			if(orderDetails.getOfferApplied()) {
				return orderDetails;
			}
			
			this.offerMap = vendorOffer.getOffers(vendorId);

			Offer tmp = offerMap.get(orderDetails.getOfferId());
			if (tmp != null && "BOGO".equals(tmp.getOfferType().toString())) {
				int originalQty = orderDetails.getQuntity();
				int freeQty = originalQty; // For BOGO, 1 free per 1 bought
				// Option 1: Adjust total quantity (including free)
				orderDetails.setQuntity(originalQty + freeQty);
				System.err.println("Offer Apply");
				orderDetails.setOfferApplied(true);
			}else {
				return orderDetails;
			}

		} catch (Exception e) {
			System.err.println(e.getMessage() + " Error at BOGO offer implimentation");
		}
		return orderDetails;

	}

}
