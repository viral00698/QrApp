package com.QrApplication.Repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.QrApplication.Entity.Offer;
import com.QrApplication.Entity.Product;

@Repository
public interface OfferRepository extends JpaRepository<Offer, UUID>{
	
	 List<Offer> findByVendorIdAndIsActiveTrueAndExpireDateGreaterThan(UUID vendorId, long currentTime);
	 
	 
	 @Query("SELECT o FROM Offer o JOIN o.products p WHERE o.vendorId = :vendorId AND o.isActive = true AND p IN :products")
	 List<Offer> findByVendorIdAndIsActiveAndProductsIn(@Param("vendorId") UUID vendorId, @Param("products") List<Product> products);


}
