package com.QrApplication.Entity;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Builder
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
	private String photo;
	private Boolean status;
	
	@OneToMany(mappedBy = "vendor", fetch = FetchType.EAGER)
	@JsonManagedReference
	private Set<Address> address= new HashSet<>();
	
}
