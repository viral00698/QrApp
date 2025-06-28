package com.QrApplication.Controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.QrApplication.AuthSecret.ResponseType;
import com.QrApplication.Dtos.GenrateInvoiceDto;
import com.QrApplication.Entity.TableOrder;
import com.QrApplication.Enum.RequestStatus;
import com.QrApplication.Service.InvoicePdfService;
import com.QrApplication.Service.TableOrderService;

@RestController
@RequestMapping("api/v1/tableOrder")
public class TableOrderController {

	@Autowired
	private TableOrderService tableOrderService;
	
	@Autowired
	private InvoicePdfService invoicePdfService;
	
	@PostMapping("addTable")
	public ResponseType addTable(@RequestBody TableOrder tableOrder) {
		
		if(tableOrder.getTableName()!=null && tableOrder.getVendorId()!=null)
			return this.tableOrderService.addTable(tableOrder);
		return ResponseType.ResponseGenerator(RequestStatus.failure, "Invalid Request");
	}
	
	@GetMapping("getTableByVendor/{vendorId}")
	public ResponseType getTableByVendor(@PathVariable String vendorId) {
		try {
			if(vendorId!=null)
				return this.tableOrderService.getTableByVendorId( UUID.fromString(vendorId));
		}catch (Exception e) {
			return ResponseType.ResponseGenerator(RequestStatus.failure, "Invalid Request"); 
		}
		return ResponseType.ResponseGenerator(RequestStatus.failure, "Invalid Request");
	}
	
	@GetMapping("deleteTable/{tableId}")
	public ResponseType deleteTableByVendorId(@PathVariable String tableId) {
		try {
			if(tableId!=null)
				return this.tableOrderService.deleteTableByVendorId( UUID.fromString(tableId));
		}catch (Exception e) {
			return ResponseType.ResponseGenerator(RequestStatus.failure, "Invalid Request"); 
		}
		return ResponseType.ResponseGenerator(RequestStatus.failure, "Invalid Request");
	}
	
	@PostMapping("createRozerPayOrder")
	public ResponseType createRozerpayOrderForTable(@RequestBody GenrateInvoiceDto genrateInvoiceDto) {

		try {
			if(genrateInvoiceDto!=null)
				return this.tableOrderService.createRozerpayOrderForTable( genrateInvoiceDto);
		}catch (Exception e) {
			return ResponseType.ResponseGenerator(RequestStatus.failure, "Invalid Request"); 
		}
		return ResponseType.ResponseGenerator(RequestStatus.failure, "Invalid Request");
	}
	
	@PostMapping("updateTableStatus")
	public ResponseType updateTableStatus(@RequestBody TableOrder tableOrder) {
		try {
			if(tableOrder!=null) {
				return this.tableOrderService.updateTableStatus(tableOrder);
			}
				
		}catch (Exception e) {
			return ResponseType.ResponseGenerator(RequestStatus.failure, "Invalid Request"); 
		}
		return ResponseType.ResponseGenerator(RequestStatus.failure, "Invalid Request");
	}
	
	@PostMapping("genrateInvoice")
	public ResponseType genrateInvoice(@RequestBody GenrateInvoiceDto genrateInvoiceDto) {
		try {
			if(genrateInvoiceDto!=null) {
				return this.invoicePdfService.print(genrateInvoiceDto);
			}
				
		}catch (Exception e) {
			return ResponseType.ResponseGenerator(RequestStatus.failure, "Invalid Request"); 
		}
		return ResponseType.ResponseGenerator(RequestStatus.failure, "Invalid Request");
	}
	
	
}
