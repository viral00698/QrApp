package com.QrApplication.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.QrApplication.Dtos.ProductImagePath;
import com.QrApplication.Entity.Product;

@Service
public class ImageStoreInDirectory {

	
//	@Value("${product.image.dir}")
//	private String PRODUCT_IAMGE_DIR;
	
	public void saveProductImage(Product product) {

		Path directoryPath = Paths.get(ProductImagePath.PRODUCT_IAMGE_DIR);
		if (!Files.exists(directoryPath)) {
			try {
				Files.createDirectories(directoryPath);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (product != null && product.getImage() != null && !product.getImage().equals("Image")) {
			String tmp = product.getImage().split(",")[1];
			byte[] imageBytes = Base64.getDecoder().decode(tmp);
			String path = ProductImagePath.PRODUCT_IAMGE_DIR + product.getItemName() + ".png";
			;
			try (FileOutputStream fos = new FileOutputStream(new File(path))) {
				fos.write(imageBytes);
				fos.flush();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

//		Path filePath = Paths.get(ProductImagePath.PRODUCT_IAMGE_DIR + product.getItemName() + ".png");

	}

	public String getProductImage(String image) {

		try {
			String imagePath = ProductImagePath.PRODUCT_IAMGE_DIR + image + ".png";
			Path path = Paths.get(imagePath);

			if (Files.exists(path)) {
				byte[] imageBytes = Files.readAllBytes(path);
				return "data:image/png;base64," + Base64.getEncoder().encodeToString(imageBytes);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
}
