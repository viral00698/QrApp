package com.QrApplication.Entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "vendorId")
public class Vendor {
		
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID vendorId;
	
	@Column(nullable = false)
	private String storeName;
	
	@Column(nullable = false)
	private String ownerName;
	private String gstNo;
	private String fssiNo;
	private Double gstCharge; // in %
	private Double sgstCharge; // in %
	private Double resturentCharge; // in %
	private String photo;
	private Boolean status;
	private Date createAt;
	
	private String upa;
	private String rk;
	private String sk;
	
//	@OneToMany(mappedBy = "vendorDetails", fetch = FetchType.EAGER)
//	@JsonManagedReference(value = "vendor-user")
//	@JsonIgnore
//	private Set<Users> users = new HashSet<>();
	
	@OneToMany(mappedBy = "productId", fetch = FetchType.LAZY)
//	@JsonManagedReference(value = "product-vendor")
	private Set<Product> product = new HashSet<>();
	
	
	@OneToMany(mappedBy = "vendor", fetch = FetchType.LAZY)
//	@JsonManagedReference(value = "vendor-address")
	private  Set<Address> address= new HashSet<>();
	
	
	public Vendor(UUID id , String storeName) {
		this.vendorId = id;
		this.storeName = storeName;
	}}
