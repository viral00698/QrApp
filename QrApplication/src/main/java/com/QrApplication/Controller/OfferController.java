package com.QrApplication.Controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.QrApplication.AuthSecret.ResponseType;
import com.QrApplication.Entity.Offer;
import com.QrApplication.Enum.RequestStatus;
import com.QrApplication.Service.OfferService;

@RestController
@RequestMapping("offer")
public class OfferController {
	
	@Autowired
	private OfferService offerService;
	
	@PostMapping("create")
	ResponseType createOffer(@RequestBody Offer offer) {
		try {
			return offerService.createOffer(offer);
		} catch (Exception e) {
			return ResponseType.ResponseGenerator(RequestStatus.failure, e);
		}
	}
	
	@PostMapping("activeOffer")
	ResponseType activeOffer(@RequestBody Offer offer) {
		try {
			return offerService.activeOffer(offer);
		} catch (Exception e) {
			return ResponseType.ResponseGenerator(RequestStatus.failure, e);
		}
	}
	
	
	@GetMapping("getOfferByVendor/{vid}")
	ResponseType getOfferByVendor(@PathVariable("vid") String vendorId) {
		return offerService.getOfferByVendor(UUID.fromString(vendorId));
	}

}
