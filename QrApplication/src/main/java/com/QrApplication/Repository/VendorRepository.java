package com.QrApplication.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.QrApplication.Entity.Vendor;

public interface VendorRepository extends JpaRepository<Vendor, UUID> {

}
