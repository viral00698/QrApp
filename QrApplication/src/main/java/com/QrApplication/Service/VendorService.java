package com.QrApplication.Service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.QrApplication.AuthSecret.ResponseType;
import com.QrApplication.Entity.Vendor;
import com.QrApplication.Enum.RequestStatus;
import com.QrApplication.Repository.VenderRepository;

@Service
public class VendorService {

	@Autowired
	private VenderRepository venderRepository;
	
	public ResponseType getById(UUID id) {
		Vendor vendor = new Vendor();
		vendor = venderRepository.findById(id).get();
		if(vendor!=null) {
			return ResponseType.ResponseGenerator(RequestStatus.success, vendor);
		}
		return ResponseType.ResponseGenerator(RequestStatus.failure, "Invalid Request");
	}

}
