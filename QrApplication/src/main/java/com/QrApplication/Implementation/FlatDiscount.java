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
public class FlatDiscount implements ApplyOffer {

	@Autowired
	private VendorOffer vendorOffer;

	HashMap<UUID, Offer> offerMap;

	public FlatDiscount() {
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
			if (tmp != null && "FLAT_DISCOUNT".equals(tmp.getOfferType().toString()) && orderDetails.getAmount() > tmp.getMinOrderAmount()) {
				double discount = tmp.getFlatDiscount(); // e.g., ₹50 off
				double totalPrice = orderDetails.getAmount() * orderDetails.getQuntity();

				// Apply flat discount to total
				double finalAmount = totalPrice - (discount * orderDetails.getQuntity());

				// Don't allow negative prices
				if (finalAmount < 0) {
					finalAmount = 0;
				}

				orderDetails.setAmount(finalAmount); // Optional: You can add this field
//			    orderDetails.setDiscount(discount);        // Optional: Set discount field if you have one
				orderDetails.setOfferApplied(true);
				System.err.println("Flat discount applied: ₹" + (discount * orderDetails.getQuntity()));
			}else {
				return orderDetails;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Geeting error while set flat discount "  + e.getMessage());
		}
		
		return orderDetails;

	}
}
