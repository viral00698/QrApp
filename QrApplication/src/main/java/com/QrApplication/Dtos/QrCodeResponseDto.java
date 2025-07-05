package com.QrApplication.Dtos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class QrCodeResponseDto {
	 	@JsonProperty("fixed_amount")
	    private boolean fixedAmount;
	    
	    private List<String> notes;
	    
	    @JsonProperty("image_url")
	    private String imageUrl;
	    
	    private String usage;
	    
	    @JsonProperty("created_at")
	    private long createdAt;
	    
	    @JsonProperty("payment_amount")
	    private int paymentAmount;
	    
	    private String description;
	    
	    @JsonProperty("payments_count_received")
	    private int paymentsCountReceived;
	    
	    private String type;
	    
	    @JsonProperty("payments_amount_received")
	    private int paymentsAmountReceived;
	    
	    @JsonProperty("tax_invoice")
	    private List<String> taxInvoice;
	    
	    private String name;
	    
	    private String id;
	    
	    @JsonProperty("customer_id")
	    private String customerId;
	    
	    @JsonProperty("close_by")
	    private long closeBy;
	    
	    private String entity;
	    
	    private String status;
}
