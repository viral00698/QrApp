package com.QrApplication.Entity;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import com.QrApplication.Enum.OrderStatus;
import com.QrApplication.Enum.PaymentMode;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
public class Orders {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID orderId;
	
	private UUID customerUUID;
	
	private String customerMobileNo;
	
	@Column(nullable = false)
	private String token_no;
	
	private String txid;
	
	@Column(nullable = false)
	@OneToMany(mappedBy = "orderId", fetch = FetchType.EAGER)
	@JsonManagedReference
	private Set<OrderDetails> orderDetails;
	
	@Column(nullable = false)
	private Date orderAt;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private PaymentMode payment_mode;
	
	@Column(nullable = false)
	private UUID vendorId;
	
	@Column(nullable = false)
	private Double totelAmount;
	
	@Column(nullable = false)
	private Double gst; //in Rs.
	
	@Column(nullable = false)
	private Double sgst; // in Rs.
	
	@Column(nullable = false)
	private Double restaurantsCharge; // in Rs.
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;

	@Override
	public String toString() {
		return "Orders [orderId=" + orderId + ", customerUUID=" + customerUUID + ", customerMobileNo="
				+ customerMobileNo + ", token_no=" + token_no + ", txid=" + txid + ", orderDetails=" + orderDetails
				+ ", orderAt=" + orderAt + ", payment_mode=" + payment_mode + ", vendorId=" + vendorId
				+ ", totelAmount=" + totelAmount + ", gst=" + gst + ", sgst=" + sgst + ", restaurantsCharge="
				+ restaurantsCharge + ", orderStatus=" + orderStatus + "]";
	}
	
	
	
	
}
