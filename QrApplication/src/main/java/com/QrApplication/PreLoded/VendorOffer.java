package com.QrApplication.PreLoded;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.QrApplication.Entity.Offer;
import com.QrApplication.Repository.OfferRepository;

@Service
public class VendorOffer {

	@Autowired
	private OfferRepository offerRepository;

	public HashMap<UUID, Offer> getOffers(UUID vendorId) {
		HashMap<UUID, Offer> offerMap = new HashMap<>();
		try {
			long currentMillis = System.currentTimeMillis();
			List<Offer> res = this.offerRepository
					.findByVendorIdAndIsActiveTrueAndExpireDateGreaterThan(vendorId, currentMillis);
			for (Offer o : res) {
				offerMap.put(o.getOfferId(), o);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return offerMap;
	}

}
