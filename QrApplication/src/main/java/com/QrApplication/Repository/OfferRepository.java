package com.QrApplication.Repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.QrApplication.Entity.Offer;

@Repository
public interface OfferRepository extends JpaRepository<Offer, UUID>{
	
	 List<Offer> findByVendorIdAndIsActiveTrueAndExpireDateGreaterThan(UUID vendorId, long currentTime);

}
