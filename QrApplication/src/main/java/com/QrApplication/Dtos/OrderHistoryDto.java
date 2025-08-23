package com.QrApplication.Dtos;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.QrApplication.Entity.Orders;

import lombok.Data;

@Data
public class OrderHistoryDto {

	private List<Orders> orders;
	private Map<UUID, String> product;
}
