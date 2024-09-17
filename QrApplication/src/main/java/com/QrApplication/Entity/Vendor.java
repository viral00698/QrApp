package com.QrApplication.Entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
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
	private Double ResturentCharge; // in %
	private String photo;
	private Boolean status;
	private Date createAt;
	
	@OneToMany(mappedBy = "vendorDetails", fetch = FetchType.EAGER)
	@JsonManagedReference
	@JsonIgnore
	private Set<Users> users = new HashSet<>();
	
	@OneToMany(mappedBy = "vendor", fetch = FetchType.EAGER)
	@JsonManagedReference
	private  Set<Address> address= new HashSet<>();
	
	
	public Vendor(UUID id , String storeName) {
		this.vendorId = id;
		this.storeName = storeName;
	}
	
}
