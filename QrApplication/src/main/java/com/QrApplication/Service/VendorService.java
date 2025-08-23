package com.QrApplication.Service;

import java.util.Optional;
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

//	public ResponseType getById(UUID id) {
//		Vendor vendor = new Vendor();
//		try {
//			vendor = venderRepository.findById(id).get();
//
//		} catch (Exception e) {
//			System.err.println(e.getMessage());
//		}
//
//		if (vendor != null) {
//			return ResponseType.ResponseGenerator(RequestStatus.success, vendor);
//		}
//		return ResponseType.ResponseGenerator(RequestStatus.failure, "Invalid Request");
//	}

	public ResponseType getById(UUID id) {
		try {
			Optional<Vendor> optionalVendor = venderRepository.findById(id);

			if (optionalVendor.isPresent()) {
				return ResponseType.ResponseGenerator(RequestStatus.success, optionalVendor.get());
			} else {
				return ResponseType.ResponseGenerator(RequestStatus.failure, "Vendor not found for ID: " + id);
			}

		} catch (Exception e) {
			// Log the error properly instead of printing to stderr
			// You can use a logger here, e.g., LoggerFactory.getLogger(this.getClass())
			e.printStackTrace(); // replace with logger.error("Error fetching vendor by ID", e);

			return ResponseType.ResponseGenerator(RequestStatus.failure, "Internal server error occurred");
		}
	}

}
