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
import com.QrApplication.Dtos.VendorDto;
import com.QrApplication.Enum.RequestStatus;
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
	
	@GetMapping("getEmailbyMobile/{mobile}/{vid}")
	ResponseType getEmailbyMobile(@PathVariable String mobile, @PathVariable String vid) {
		try {
			return createVendorService.getEmailbyMobile(mobile, UUID.fromString(vid));
		} catch (Exception e) {
			return ResponseType.ResponseGenerator(RequestStatus.failure, "Invalid vendor");
		}
	}
	
	
}
