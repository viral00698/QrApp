package com.QrApplication.Entity;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "addressId")
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
//	@JsonBackReference
	private Users users;
	
	@ManyToOne
	@JoinColumn(name = "vendorId")
//	@JsonBackReference(value = "user-vender")
	private Vendor vendor;
	
}
