package com.QrApplication.Controller;

import java.io.File;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpHeaders;


@RestController
public class InvoiceDownload {

	
	 @Value("${product.invoice.dir:/home/ubuntu/invoice/}")
	 String pdfPath;
	
	 @GetMapping("/invoice/{orderId}")
	    public ResponseEntity<?> downloadInvoice(@PathVariable String orderId) {
		 
		 	if(orderId.isBlank()) {
		 		  return ResponseEntity.badRequest()
			                .body(null);
		 	}
		 	String first8Digits = orderId.length() > 8 ? orderId.substring(0, 8) : orderId;
		 	
		 	String filePath = pdfPath + File.separator + "invoice_" + first8Digits + ".pdf";
		    File file = new File(filePath);
	        
	        System.err.println(file);
	        if (!file.exists()) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	        }

	        FileSystemResource resource = new FileSystemResource(file);
	        return ResponseEntity.ok()
	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=invoice_" + orderId + ".pdf")
	                .body(resource);
	    }
}
