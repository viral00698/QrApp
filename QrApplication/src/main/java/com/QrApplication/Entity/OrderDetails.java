package com.QrApplication.Entity;

import java.util.UUID;

import com.QrApplication.Enum.FoodCategory;
import com.QrApplication.Enum.OfferType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class OrderDetails {
	
	
	public OrderDetails() {
	    this.offerApplied = false;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID orderDetailsId;
	
	@Column(nullable = false)
	private String itemName;
	
	@Column(nullable = false)
	private UUID productId;

	@Enumerated(EnumType.STRING)
	private FoodCategory foodCategory;
	
	@Column(nullable = false)
	private Double amount;
	
	@Column(nullable = false)
	private Boolean isJain;
	
	@Column(nullable = false)
	private Integer quntity;
	
	@ManyToOne
	@JoinColumn(name = "orderId")
	@JsonBackReference
	private Orders orderId;
	
	private UUID offerId;
	
	@Enumerated(EnumType.STRING)
	private OfferType offerType;
	
	@Transient
	private Boolean oflineOffer;
	
	@Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
	private Boolean offerApplied;
	
	@Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
	private Boolean isDelivered;

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
	
	
}
