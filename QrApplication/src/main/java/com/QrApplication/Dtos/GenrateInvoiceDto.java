package com.QrApplication.Dtos;

import com.QrApplication.Entity.Orders;
import com.QrApplication.Entity.TableOrder;
import com.QrApplication.Entity.Vendor;

import lombok.Data;

@Data
public class GenrateInvoiceDto {
		Vendor vendor;
		Orders order;
//		TableOrder tableOrder;
}
