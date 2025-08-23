package com.QrApplication.Dtos;

import java.util.UUID;

import lombok.Data;

@Data
public class AddressDto {
	
	 	private String addressId;
	    private String state;
	    private String dist;
	    private String taluka;
	    private String villageStreet;
	    private String pincode;
	    

	    private UUID userId;     // Reference to Users
	    private UUID vendorId;   // Reference to Vendor
}
