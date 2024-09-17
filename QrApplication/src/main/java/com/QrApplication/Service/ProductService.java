package com.QrApplication.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.QrApplication.AuthSecret.ResponseType;
import com.QrApplication.Entity.Product;
import com.QrApplication.Entity.Users;
import com.QrApplication.Enum.RequestStatus;
import com.QrApplication.Repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	public ResponseType getProductList(Users users) {
		
		List<Product> res = this.productRepository.findByUsers(users);
		if(!res.isEmpty()) {
			return ResponseType.ResponseGenerator(RequestStatus.success, res);
		}else {
			return ResponseType.ResponseGenerator(RequestStatus.failure, "Data Not Available");
		}

		
	}
}
