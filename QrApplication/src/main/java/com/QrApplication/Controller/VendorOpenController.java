package com.QrApplication.Controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.QrApplication.AuthSecret.ResponseType;

import com.QrApplication.Service.VendorService;

@RestController
@RequestMapping("vendor")
public class VendorOpenController {
	
	@Autowired
	private VendorService vendorService;
	
	@GetMapping("qr/getVendor/{id}")
	ResponseType getById(@PathVariable UUID id){
		System.err.println(id);
		return this.vendorService.getById(id);
	}
}
