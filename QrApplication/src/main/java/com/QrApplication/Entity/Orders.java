package com.QrApplication.Entity;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import com.QrApplication.Dtos.BillingDtos;
import com.QrApplication.Enum.AppType;
import com.QrApplication.Enum.OrderStatus;
import com.QrApplication.Enum.PaymentMode;
import com.QrApplication.Enum.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.razorpay.QrCode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private AppType appType;
	
	@Column(nullable = false)
	private String restroName;
	
//	private UUID tableId;
	
	private String customerName;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "pay_id")
	private PaymentDetail paymentDetail;
	
//	@OneToOne(mappedBy = "orders")
//	private TableOrder tableOrder;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "tableId")
	private TableOrder tableOrder;
	
	@Enumerated(EnumType.STRING)
	private PaymentStatus paymentStatus;
	
	@Transient
	@JsonSerialize(using = ToStringSerializer.class)
	private QrCode qrCode;
	
	@Transient
	private String txnNo;
	
	@Transient
	private String refNo;
	
	private String OfferId;
	

	@Transient
	private BillingDtos billingDtos;

	@Override
	public String toString() {
		return "Orders [orderId=" + orderId + ", customerUUID=" + customerUUID + ", customerMobileNo="
				+ customerMobileNo + ", token_no=" + token_no + ", txid=" + txid + ", orderDetails=" + orderDetails
				+ ", orderAt=" + orderAt + ", payment_mode=" + payment_mode + ", vendorId=" + vendorId
				+ ", totelAmount=" + totelAmount + ", gst=" + gst + ", sgst=" + sgst + ", restaurantsCharge="
				+ restaurantsCharge + ", orderStatus=" + orderStatus + ", tableOrder=" + tableOrder + "]";
	}
	
	
	
	
}
