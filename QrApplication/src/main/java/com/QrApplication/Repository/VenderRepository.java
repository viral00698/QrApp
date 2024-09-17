package com.QrApplication.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.QrApplication.Entity.Vendor;

@Repository
public interface VenderRepository extends JpaRepository<Vendor, UUID> {

	@Query("SELECT new Vendor(v.vendorId, v.storeName) FROM Vendor v")
	public Vendor getStorenameAndId();
	
}
