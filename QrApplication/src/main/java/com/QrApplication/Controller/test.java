package com.QrApplication.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.QrApplication.Service.InvoicePdfService;

@RestController
public class test {
	
//	@GetMapping("pg")
//	 public ResponseEntity<String> handleUpiResponse(
//	            @RequestParam String status,
//	            @RequestParam(required = false) String txnId,
//	            @RequestParam(required = false) String txnRef,
//	            @RequestParam(required = false) String ApprovalRefNo) {
//	        
//	        // Log response details
//	        System.out.println("UPI Payment Response:");
//	        System.out.println("Status: " + status);
//	        System.out.println("Transaction ID: " + txnId);
//	        System.out.println("Transaction Ref: " + txnRef);
//	        System.out.println("Approval Ref No: " + ApprovalRefNo);
//	        
//	        // Process payment status
//	        if ("SUCCESS".equalsIgnoreCase(status)) {
//	            // Save payment details to database
//	            return ResponseEntity.ok("Payment Successful! Transaction ID: " + txnId);
//	        } else {
//	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment Failed");
//	        }
//	    }
	
//	@GetMapping("pg1")
//	public String invoice() {
//		InvoicePdfService invoicePdfService = new InvoicePdfService();
////		invoicePdfService.printG();
//		return "true";
//	}
	
	@GetMapping("pg")
	public String test() {
		return "Hello I am Working";
	}
}
