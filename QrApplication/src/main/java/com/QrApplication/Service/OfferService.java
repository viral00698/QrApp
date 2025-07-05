package com.QrApplication.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.QrApplication.AuthSecret.ResponseType;
import com.QrApplication.Dtos.OfferDto;
import com.QrApplication.Entity.Offer;
import com.QrApplication.Enum.RequestStatus;
import com.QrApplication.Repository.OfferRepository;

@Service
public class OfferService {
	
	@Autowired
	private OfferRepository offerRepository;

	public ResponseType createOffer(Offer offer) {
		
		try {
			
			if(offer.getOfferType()!=null && offer.getVendorId()!=null) {
				offer.setCreateAt(new Date());
				offerRepository.save(offer);
				return ResponseType.ResponseGenerator(RequestStatus.success, "Offer saved");
			}else {
				return ResponseType.ResponseGenerator(RequestStatus.failure, "Data insuffcient");
			}
			
		} catch (Exception e) {
			return ResponseType.ResponseGenerator(RequestStatus.failure, "Error getting while save offer data");
		}
	}

	public ResponseType activeOffer(Offer offer) {
		
		return null;
	}
	
	public ResponseType getOfferByVendor(UUID vendorId) {
	    try {
	        if (vendorId == null) {
	            return ResponseType.ResponseGenerator(RequestStatus.failure, "Vendor ID must not be null.");
	        }

//	        long currentMillis = System.currentTimeMillis();
	        List<Offer> res = offerRepository.findByVendorId(vendorId);

	        if (res.isEmpty()) {
	            return ResponseType.ResponseGenerator(RequestStatus.success, "No active offers found.");
	        }

	        return ResponseType.ResponseGenerator(RequestStatus.success, res);

	    } catch (Exception e) {
	        e.printStackTrace(); // Optional: log to logger instead
	        return ResponseType.ResponseGenerator(RequestStatus.failure, "Failed to fetch offers: " + e.getMessage());
	    }
	}

	public ResponseType setOfferStatus(OfferDto offerDto) {
		try {
			
			int res = offerRepository.setOfferStatus(offerDto.getIsActive() , offerDto.getVendorId() , offerDto.getOfferId());
			if(res > 0) {
				  return ResponseType.ResponseGenerator(RequestStatus.success, "Offer Status Update");
			}
			
		} catch (Exception e) {
			return ResponseType.ResponseGenerator(RequestStatus.failure, "Geting Error while update offer status");
		}
		 return ResponseType.ResponseGenerator(RequestStatus.failure, "Invalid Requset");
	}


}
