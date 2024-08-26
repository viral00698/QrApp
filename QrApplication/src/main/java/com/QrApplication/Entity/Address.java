package com.QrApplication.Entity;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Builder
public class Address {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID addressId;
	
	private String state;
	private String dist;
	private String taluka;
	private String villageStreet;
	private String pincode;
	
	@ManyToOne
	@JoinColumn(name = "id")
	@JsonBackReference
	private Users users;
	
	@ManyToOne
	@JoinColumn(name = "vendorId")
	@JsonBackReference
	private Vendor vendor;
	
}
