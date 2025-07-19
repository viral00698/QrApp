package com.QrApplication.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.QrApplication.AuthSecret.ResponseType;
import com.QrApplication.Dtos.VendorDto;
import com.QrApplication.Service.CreateVendorService;

@RestController
@RequestMapping("cv")
public class CreateVendorController {
	
	@Autowired
	private CreateVendorService createVendorService;
	
	@PostMapping("create")
	ResponseType createVendor(@RequestBody VendorDto vendorDto) {
		return createVendorService.createVendor(vendorDto);
	}
	
	@GetMapping("getVendors")
	ResponseType getVendors() {
		return createVendorService.getVendors();
	}
	
	@PostMapping("chnageVendorStatus")
	ResponseType chnageVendorStatus(@RequestBody VendorDto vendorDto) {
		return createVendorService.changeVendorStatus(vendorDto);
	}
	
	
}
