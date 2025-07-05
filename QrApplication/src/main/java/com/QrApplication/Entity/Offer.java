package com.QrApplication.Entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.QrApplication.Enum.OfferType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@Entity
public class Offer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID offerId;
	
	@Column(nullable = false)
	private String offerName;
	
	@Column(nullable = false)
	private Boolean isActive;
	
	@Column(nullable = false)
    @Enumerated(EnumType.STRING)
	private OfferType offerType;
	
	private Double discountBypercentage;
	
	private Double fixAmount;
	
	private Double flatDiscount;
	
	private Double minOrderAmount;
	
	private String message;
	
	private long expireDate;
	
	@Column(nullable = false)
	private UUID vendorId;
	
	private UUID freeItem;
	
	private Date createAt;
	
	@OneToMany(mappedBy = "offer", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Product> products = new HashSet<>();

	
	
}
