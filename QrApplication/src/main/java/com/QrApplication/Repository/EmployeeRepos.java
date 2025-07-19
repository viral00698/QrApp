package com.QrApplication.Repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.QrApplication.Entity.Employee;
import com.QrApplication.Entity.Vendor;

import jakarta.transaction.Transactional;

@Repository
public interface EmployeeRepos extends JpaRepository<Employee, UUID>{
	
	List<Employee> findByVendor(Vendor vendor);
	
	@Transactional
	@Modifying
	@Query("UPDATE Employee e SET e.status = :isActive WHERE e.vendor.vendorId = :vendorId AND e.empId = :empId")
	int changeEmployeeStatus(@Param("isActive") Boolean isActive,
	                         @Param("vendorId") UUID vendorId,
	                         @Param("empId") UUID empId);

}
