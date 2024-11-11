package com.QrApplication.Service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.QrApplication.AuthSecret.ResponseType;
import com.QrApplication.Entity.Product;
import com.QrApplication.Entity.Vendor;
import com.QrApplication.Enum.RequestStatus;
import com.QrApplication.Repository.OrderRepository;
import com.QrApplication.Repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ImageStoreInDirectory imageStoreInDirectory;

	public ResponseType getProductList(UUID id) {

		if (!id.equals(null)) {
			Vendor vendor = new Vendor();
			vendor.setVendorId(id);
			List<Product> res = this.productRepository.findByVendorAndStatus(vendor , true);
			if (!res.isEmpty()) {
				return ResponseType.ResponseGenerator(RequestStatus.success, res);
			}
		}

		return ResponseType.ResponseGenerator(RequestStatus.failure, "Data Not Available");

	}

	public ResponseType getOrdersByCustomerId(String customerId) {
		if (!customerId.equals(null))
			return ResponseType.ResponseGenerator(RequestStatus.success,
					this.orderRepository.findByCustomerUUID(UUID.fromString(customerId)));
		return ResponseType.ResponseGenerator(RequestStatus.failure, "Invalid Request");

	}

	public ResponseType getVendorProduts(UUID venderId) {
		try {
			Vendor vendor = new Vendor();
			vendor.setVendorId(venderId);

			List<Product> res = this.productRepository.findByVendor(vendor);
			res.forEach(item -> {
				if (!item.getImage().equals(null)) {
					String resImage = this.imageStoreInDirectory.getProductImage(item.getItemName());
					item.setImage(resImage);
				}

			});

			return ResponseType.ResponseGenerator(RequestStatus.success, res);
		} catch (Exception e) {
			return ResponseType.ResponseGenerator(RequestStatus.failure, "Request getError");
		}

	}
	
	
	public ResponseType updateProductStatus(UUID  id, UUID vendorId , Boolean status) {
		
		int x = this.productRepository.updateProductStatus(status , id, vendorId);
		if(x>0) {
			return ResponseType.ResponseGenerator(RequestStatus.success, "Product status successfully updated");
		}else {
			return ResponseType.ResponseGenerator(RequestStatus.failure, "Product status update failed. Please try again.");
		}
		
	}

	public ResponseType addProduct(Product product) {

		if (product.getProductId() != null) {
			Product alreadyExist = this.productRepository.findById(product.getProductId()).get();
			alreadyExist.setAmount(product.getAmount());
			alreadyExist.setDescription(product.getDescription());
			alreadyExist.setGram(product.getGram());

			alreadyExist.setItemName(product.getItemName());
			alreadyExist.setJain(product.getJain());
			alreadyExist.setQuantity(product.getQuantity());
			alreadyExist.setStatus(product.getStatus());
			alreadyExist.setVegNonVeg(product.getVegNonVeg());

			if (product != null && product.getImage()!=null && product.getImage().startsWith("data:image/")) {
				imageStoreInDirectory.saveProductImage(product);
				alreadyExist.setImage(product.getItemName());
			}
			
			Product tmp = this.productRepository.save(alreadyExist);
			if (tmp != null)
				return ResponseType.ResponseGenerator(RequestStatus.success, "Product added successfully");
			return ResponseType.ResponseGenerator(RequestStatus.failure, "Failed to add product. Please try again!");

		}

		if (product != null && product.getAmount() != null && product.getVendor() != null
				&& product.getProductId() == null) {

			if (product.getImage() != null && product.getImage().startsWith("data:image/")) {
				imageStoreInDirectory.saveProductImage(product);
				product.setImage(product.getItemName());
			}
			
			Product tmp = this.productRepository.save(product);
			if (tmp != null)
				return ResponseType.ResponseGenerator(RequestStatus.success, "Product added successfully");
			return ResponseType.ResponseGenerator(RequestStatus.failure, "Failed to add product. Please try again!");
		}
		return ResponseType.ResponseGenerator(RequestStatus.success, "Request Invalid");
	}

	public ResponseType deleteProductByid(UUID id, UUID vender) {
		int x = this.productRepository.deleteProductByid(id, vender);
		if(x>0) {
			return ResponseType.ResponseGenerator(RequestStatus.success, "Product successfully deleted");
		}else {
			return ResponseType.ResponseGenerator(RequestStatus.failure, "Product deletion failed. The product with ID");
		}
	}

}
