package com.QrApplication.Implementation;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.QrApplication.Entity.Offer;
import com.QrApplication.Entity.OrderDetails;
import com.QrApplication.Entity.Product;
import com.QrApplication.Interface.ApplyOffer;
import com.QrApplication.PreLoded.VendorOffer;
import com.QrApplication.Repository.ProductRepository;

@Component
public class ByXGetY implements ApplyOffer{
	
	@Autowired
	private VendorOffer vendorOffer;

	HashMap<UUID, Offer> offerMap;
	
	@Autowired
	private ProductRepository productRepository;

	public ByXGetY() {
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
				
				double minOrderAmount = Optional.ofNullable(tmp.getMinOrderAmount()).orElse(0.0);

				if(orderDetails.getAmount() > minOrderAmount)
					return null;
				
				UUID freeItem =  tmp.getFreeItem();
			    Product product = productRepository.findById(freeItem).get();
			    
			    if(product!=null) {
			    	
			    	OrderDetails getFreeItem = new OrderDetails();
			    	getFreeItem.setAmount(0.0);
			    	getFreeItem.setFoodCategory(product.getFoodCategory());
			    	getFreeItem.setIsJain(product.getJain());
			    	getFreeItem.setProductId(product.getProductId());
			    	getFreeItem.setQuntity(1);
			    	getFreeItem.setItemName(product.getItemName());
			    	return getFreeItem;
			    }
				
		} catch (Exception e) {
			System.err.println(e.getMessage() + " Error at ByXGetY offer implimentation");
		}
		
		return null;
	}

}
