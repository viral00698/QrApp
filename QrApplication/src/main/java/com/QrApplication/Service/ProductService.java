package com.QrApplication.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.QrApplication.AuthSecret.ResponseType;
import com.QrApplication.Dtos.ProductDto;
import com.QrApplication.Entity.Product;
import com.QrApplication.Entity.Vendor;
import com.QrApplication.Enum.RequestStatus;
import com.QrApplication.Mapper.ProductMapper;
import com.QrApplication.Repository.OrderRepository;
import com.QrApplication.Repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ProductMapper productMapper;

	@Autowired
	private ImageStoreInDirectory imageStoreInDirectory;

	public ResponseType getProductList(UUID id) {

		try {
			if (!id.equals(null)) {
				Vendor vendor = new Vendor();
				vendor.setVendorId(id);
				List<Product> res = this.productRepository.findByVendorAndStatus(vendor, true);
				if (!res.isEmpty()) {

					res.forEach(item -> {
						if (item.getImage() != null) {
							String resImage = this.imageStoreInDirectory.getProductImage(item.getItemName());
							item.setImage(resImage);
						}
					});
					return ResponseType.ResponseGenerator(RequestStatus.success, res);
				}
			}

		} catch (Exception e) {
			return ResponseType.ResponseGenerator(RequestStatus.failure, "Geting error while fatching menu");
		}

		return ResponseType.ResponseGenerator(RequestStatus.failure, "Data Not Available");

	}

	public ResponseType getOrdersByCustomerId(String customerId) {
		if (!customerId.equals(null)) {
			return ResponseType.ResponseGenerator(RequestStatus.success,
					this.orderRepository.findByCustomerUUID(UUID.fromString(customerId)));
		}
		return ResponseType.ResponseGenerator(RequestStatus.failure, "Invalid Request");

	}

	public ResponseType getVendorProduts(UUID venderId) {
		try {
			Vendor vendor = new Vendor();
			vendor.setVendorId(venderId);

			List<Product> res = this.productRepository.findByVendor(vendor);
			res.forEach(item -> {
				if (item.getImage() != null) {
					String resImage = this.imageStoreInDirectory.getProductImage(item.getItemName());
					item.setImage(resImage);
				}
			});

			return ResponseType.ResponseGenerator(RequestStatus.success, res);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseType.ResponseGenerator(RequestStatus.failure, "Request getError");
		}

	}

	public ResponseType updateProductStatus(UUID id, UUID vendorId, Boolean status) {

		int x = this.productRepository.updateProductStatus(status, id, vendorId);
		if (x > 0) {
			return ResponseType.ResponseGenerator(RequestStatus.success, "Product status successfully updated");
		} else {
			return ResponseType.ResponseGenerator(RequestStatus.failure,
					"Product status update failed. Please try again.");
		}

	}

	public ResponseType addProduct(ProductDto productDto) {

		if (productDto != null && productDto.getProductId() != null) {
			Optional<Product> optionalProduct = productRepository.findById(productDto.getProductId());
			if (optionalProduct.isPresent()) {
				Product existingProduct = optionalProduct.get();
				productMapper.updateProductFromDto(productDto, existingProduct);
				return editProduct(existingProduct);
			} else {
				return ResponseType.ResponseGenerator(RequestStatus.failure, "Invalid Request");
			}

		} else {
			Product p = productMapper.toEntity(productDto);
			System.err.println(p.getOffer());
			return saveProduct(p);
		}
	}

	public ResponseType deleteProductByid(UUID id, UUID vender) {
		int x = this.productRepository.deleteProductByid(id, vender);
		if (x > 0) {
			return ResponseType.ResponseGenerator(RequestStatus.success, "Product successfully deleted");
		} else {
			return ResponseType.ResponseGenerator(RequestStatus.failure,
					"Product deletion failed. The product with ID");
		}
	}

	public ResponseType saveProduct(Product product) {
		try {
			imageStoreInDirectory.saveProductImage(product);
			product.setImage(product.getItemName());
			productRepository.save(product);
			return ResponseType.ResponseGenerator(RequestStatus.success, "Product added successfully");
		} catch (Exception e) {
			return ResponseType.ResponseGenerator(RequestStatus.failure, "Get Error While save Product");
		}
	}

	public ResponseType editProduct(Product product) {
		try {
			if (product.getImage() != null && product.getImage().startsWith("data:image/")) {
				imageStoreInDirectory.saveProductImage(product);
				product.setImage(product.getItemName());
			}
			productRepository.save(product);
			return ResponseType.ResponseGenerator(RequestStatus.success, "Product edit successfully");
		} catch (Exception e) {
			return ResponseType.ResponseGenerator(RequestStatus.failure, "Get Error While edit Product");
		}
	}

}
