package com.QrApplication.Dtos;

import lombok.Data;

@Data
public class RazorpayOrder {

	private String orderId;
    private int amount;
    private String currency;
    private String status;
}
