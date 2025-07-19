package com.QrApplication.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.QrApplication.Entity.Vendor;

import jakarta.transaction.Transactional;

@Repository
public interface VenderRepository extends JpaRepository<Vendor, UUID> {

	@Query("SELECT new Vendor(v.vendorId, v.storeName) FROM Vendor v")
	public Vendor getStorenameAndId();

	@Modifying
	@Transactional
	@Query("UPDATE Vendor v SET v.status = :status WHERE v.vendorId = :vendorId ")
	int changeVendorStatus(@Param("vendorId") UUID vendorId, @Param("status") Boolean status);
	
}
