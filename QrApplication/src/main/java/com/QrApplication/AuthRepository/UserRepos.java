package com.QrApplication.AuthRepository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.QrApplication.Dtos.EmailEmpIdDto;
import com.QrApplication.Dtos.EmailVendorIdDto;
import com.QrApplication.Entity.Users;

public interface UserRepos extends JpaRepository<Users, UUID> {

	List<Users> findByEmail(String email);

	Boolean existsByEmail(String email);
	
	@Query(value = "select id from users where email=:email" , nativeQuery = true)
	UUID getIdByEmail(@Param("email")  String email);
	
	@Query(value = "select * from users where email=:email" , nativeQuery = true)
	Users getUserByEmail(@Param("email")  String email);
	
	@Query("SELECT new com.QrApplication.Dtos.EmailEmpIdDto(u.email, u.employee) " +
		       "FROM Users u WHERE u.vendorDetails.vendorId = :vid")
	List<EmailEmpIdDto> getEmailByVid(@Param("vid") UUID vid);
	
	
	@Query("SELECT new com.QrApplication.Dtos.EmailVendorIdDto(u.email, u.vendorDetails.vendorId) " +
		       "FROM Users u " +
		       "WHERE u.vendorDetails.vendorId = :vid AND u.vendorDetails.mobileNo = :mobile")
		EmailVendorIdDto getEmailbyMobile(@Param("mobile") String mobile,
		                                  @Param("vid") UUID vid);



}
