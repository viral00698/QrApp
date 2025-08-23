package com.QrApplication.Dtos;

import java.util.Date;
import java.util.UUID;

import com.QrApplication.Entity.Address;

import lombok.Data;

@Data
public class VendorDto {

	  	private UUID vendorId;
	    private String storeName;
	    private String ownerName;
	    private String gstNo;
	    private String fssiNo;
	    private Double gstCharge;
	    private Double sgstCharge;
	    private Double resturentCharge;
	    private String photo;
	    private Boolean status;
	    private String mobileNo;
	    private String upa;
	    private String rk;
	    private String sk;
	    private Date createAt;
	    private String aadharNo;
		private String aadharDoc;
		
		private String panNo;
		private String panDoc;
		private String fssiDoc;
		private String gstDoc;
		
		private Address address;
}
