package com.QrApplication.Repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.QrApplication.Entity.Product;
import com.QrApplication.Entity.Users;
import com.QrApplication.Entity.Vendor;

@EnableJpaRepositories
@Repository
public interface ProductRepository extends JpaRepository<Product, UUID>{
		
	 	@Query("SELECT p FROM Product p WHERE p.productId IN :ids AND p.status=true")
		public List<Product> checkAviliblityInDb(@Param("ids") List<UUID> ids);
	 	
	 	public List<Product> findByVendor(Vendor vendor);
	 	
	 	public List<Product> findByVendorAndStatus(Vendor vendor , Boolean status);
	 	
	 	@Modifying
	 	@Transactional
	 	@Query("UPDATE Product p SET p.status = :status WHERE p.id = :id AND p.vendor.vendorId = :vendorId")
	 	int updateProductStatus(@Param("status") Boolean status , @Param("id") UUID id, @Param("vendorId") UUID vendorId);

	 	@Modifying
	 	@Transactional
	 	@Query("DELETE FROM Product p WHERE p.productId = :id AND p.vendor.vendorId = :vender")
		public int deleteProductByid(@Param("id") UUID id, @Param("vender") UUID vender);
	 	
	 	@Query("SELECT new Product(p.productId,p.itemName) FROM Product p WHERE p.vendor.vendorId =  :vendorId")
		public List<Product> getProduct(@Param("vendorId") UUID vendorId);

}
