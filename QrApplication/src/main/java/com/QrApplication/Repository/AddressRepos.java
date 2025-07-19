package com.QrApplication.Repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.QrApplication.Entity.Address;
import com.QrApplication.Entity.Employee;

public interface AddressRepos extends JpaRepository<Address, UUID>{

	List<Address> findByEmployee(Employee emp);

}
