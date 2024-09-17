package com.QrApplication.Entity;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class OrderDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID orderDetailsId;
	
	@Column(nullable = false)
	private String itemName;
	
	@Column(nullable = false)
	private UUID productId;

	
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

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
	
	
}
