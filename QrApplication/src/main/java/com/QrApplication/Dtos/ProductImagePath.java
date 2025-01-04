package com.QrApplication.Dtos;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProductImagePath {
//   public static final String 	PRODUCT_IAMGE_DIR = "D:/QR_APP_PRODUCT_IMAGE/";
//   public static final String PRODUCT_IMAGE_DIR = "/home/ubuntu/QR_APP_PRODUCT_IMAGE/";

//   public static final String 	PRODUCT_IAMGE_DIR = "/QR_APP_PRODUCT_IMAGE/";
//   

//   @Value("${product.image.dir}")
//   public static final String PRODUCT_IMAGE_DIR;

	@Value("${product.image.dir:/home/ubuntu/QR_APP_PRODUCT_IMAGE/}") // Default value if not set
	 String productImageDir;

	public String getProductImageDir() {
		return productImageDir;
	}

}
