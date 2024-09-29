package com.QrApplication.Service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.QrApplication.AuthSecret.ResponseType;
import com.QrApplication.Entity.Product;
import com.QrApplication.Entity.Vendor;
import com.QrApplication.Enum.RequestStatus;
import com.QrApplication.Repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	public ResponseType getProductList(UUID id) {
		
		if(!id.equals(null)) {
			Vendor vendor = new Vendor();
			vendor.setVendorId(id);
			List<Product> res = this.productRepository.findByVendor(vendor);
			if(!res.isEmpty()) {
				return ResponseType.ResponseGenerator(RequestStatus.success, res);
			}
		}
		
		return ResponseType.ResponseGenerator(RequestStatus.failure, "Data Not Available");
		
		
	}
}
