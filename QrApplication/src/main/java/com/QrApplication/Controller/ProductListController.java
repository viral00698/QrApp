package com.QrApplication.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.QrApplication.AuthSecret.ResponseType;
import com.QrApplication.Service.ProductListService;

@RestController
@RequestMapping("productlist")
public class ProductListController {

	@Autowired
	private ProductListService productListService;
	
	@GetMapping("getAll")
	ResponseType getProductList(){
		return this.productListService.getProductList();
	}
}
