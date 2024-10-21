package com.QrApplication.Service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.QrApplication.AuthSecret.ResponseType;
import com.QrApplication.Entity.ProductList;
import com.QrApplication.Entity.Vendor;
import com.QrApplication.Enum.RequestStatus;
import com.QrApplication.Repository.ProductListRepos;

@Service
public class ProductListService {

	@Autowired
	private ProductListRepos productListRepos;
	
	public ResponseType getProductList() {
		
		try {
			List<ProductList> res = this.productListRepos.findAll();
			
			return ResponseType.ResponseGenerator(RequestStatus.success, res);
		} catch (Exception e) {
			return ResponseType.ResponseGenerator(RequestStatus.failure, "Request getError");
		}
		
	}
	
	
	
	

	
	
}
