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
import com.QrApplication.Dtos.ProductDto;
import com.QrApplication.Entity.Product;
import com.QrApplication.Enum.RequestStatus;
import com.QrApplication.Service.ProductService;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("product")
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping("/getByVenderId/{id}")
	public ResponseType getProductByVender(@PathVariable("id") String id) {
		return this.productService.getProductList(UUID.fromString(id));
	}

	@GetMapping("getByOrderBy/{id}")
	public ResponseType getProductByCustomer(@PathVariable("id") String id) {
		return this.productService.getOrdersByCustomerId(id);
	}

	@GetMapping("getVendorProduts/{id}")
	public ResponseType getVendorProduts(@PathVariable("id") String id) {
		return this.productService.getVendorProduts(UUID.fromString(id));
	}

	@PostMapping("/addProduct")
	public ResponseType addProduct(@RequestBody ProductDto product) {
	
		return this.productService.addProduct( product);
	}
	
	@PostMapping("updateProductStatus")
	public ResponseType updateProductStatus(@RequestBody ProductDto product) {
		System.err.println(product);
		if(product.getProductId() !=null && product.getVendorId()!=null) {
			
			UUID  id = product.getProductId();
			UUID vender = product.getVendorId();
			Boolean status = product.getStatus();
			
			return this.productService.updateProductStatus(id,vender,status);
		}
			
		return ResponseType.ResponseGenerator(RequestStatus.failure, "Invalid Request");
	}
	
	@PostMapping("deleteProductByid")
	public ResponseType deleteProductByid(@RequestBody ProductDto product) {
		try {
		    if (product !=null) {
		        
		        UUID productId = product.getProductId();
		        UUID vendorId = product.getVendorId();

		        return productService.deleteProductByid(productId, vendorId);
		    }else {
		    	 return ResponseType.ResponseGenerator(RequestStatus.failure, "Invalid Request" );

		    }
		} catch (Exception e) {
			e.printStackTrace();
		    return ResponseType.ResponseGenerator(RequestStatus.failure, "Error: " + e.getMessage());
		}

	}
	
}
