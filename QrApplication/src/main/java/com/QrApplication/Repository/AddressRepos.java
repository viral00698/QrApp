package com.QrApplication.Repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.QrApplication.Entity.Address;
import com.QrApplication.Entity.Employee;

public interface AddressRepos extends JpaRepository<Address, UUID>{

	List<Address> findByEmployee(Employee emp);

	@Query("SELECT a FROM Address a WHERE a.employee.empId IN :ids")
	List<Address> getAddressByEmployesId(@Param("ids") List<UUID> ids);

}
