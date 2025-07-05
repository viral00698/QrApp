package com.QrApplication.Dtos;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import com.QrApplication.Enum.OfferType;

import lombok.Data;

@Data
public class OfferDto {

	 	private UUID offerId;

	    private String offerName;

	    private Boolean isActive;

	    private OfferType offerType;

	    private Double discountBypercentage;

	    private Double fixAmount;

	    private Double flatDiscount;

	    private Double minOrderAmount;

	    private String message;

	    private long expireDate;

	    private UUID vendorId;

	    private UUID freeItem;

	    private Date createAt;

	    private Set<UUID> productIds; 
	
}
