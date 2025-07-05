package com.QrApplication.Factory;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.QrApplication.Entity.OrderDetails;
import com.QrApplication.Entity.Orders;
import com.QrApplication.Enum.OfferType;
import com.QrApplication.Implementation.Bogo;
import com.QrApplication.Implementation.ByXGetY;
import com.QrApplication.Implementation.FlatDiscount;

@Component
public class OfferFactory {

	private final Bogo bogo;
	private final FlatDiscount flatDiscount;
	private final ByXGetY byXGetY;

	public OfferFactory(Bogo bogo, FlatDiscount flatDiscount , ByXGetY byXGetY) {
		this.bogo = bogo;
		this.flatDiscount = flatDiscount;
		this.byXGetY = byXGetY;
		
	}

	public Orders getOffer(Orders orders, UUID vendorId) {

		Set<OrderDetails> newList = new HashSet<>();

		try {
			for (OrderDetails orderDetails : orders.getOrderDetails()) {
				
				if (orderDetails.getOfferType()!=null && OfferType.BOGO.equals(orderDetails.getOfferType())) {
					OrderDetails t = bogo.applyOffer(orderDetails, vendorId);
					newList.add(t);
				} else if ( orderDetails.getOfferType()!=null && OfferType.FLAT_DISCOUNT.equals(orderDetails.getOfferType())) {
					OrderDetails t = flatDiscount.applyOffer(orderDetails, vendorId);
					newList.add(t);
				} else if ( orderDetails.getOfferType()!=null && OfferType.BUY_X_GET_Y.equals(orderDetails.getOfferType())) {
				    System.err.println(OfferType.BUY_X_GET_Y);
					OrderDetails t = byXGetY.applyOffer(orderDetails, vendorId);
					newList.add(t);
				} else {
					newList.add(orderDetails);
				}

			}

			orders.setOrderDetails(newList);

			return orders;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}

	}
	
	public OrderDetails applyOffer(OrderDetails orderDetails, UUID vendorId) {

		try {
				
				if (orderDetails.getOfferType()!=null && OfferType.BOGO.equals(orderDetails.getOfferType())) {
					return bogo.applyOffer(orderDetails, vendorId);
				} else if ( orderDetails.getOfferType()!=null && OfferType.FLAT_DISCOUNT.equals(orderDetails.getOfferType())) {
					return flatDiscount.applyOffer(orderDetails, vendorId);
				} else if ( orderDetails.getOfferType()!=null && OfferType.BUY_X_GET_Y.equals(orderDetails.getOfferType())) {
					return byXGetY.applyOffer(orderDetails, vendorId);
				}
				else {
					return orderDetails;
				}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}

	}
}
