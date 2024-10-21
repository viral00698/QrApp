package com.QrApplication.AuthRepository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.QrApplication.Entity.Users;

public interface UserRepos extends JpaRepository<Users, UUID> {

	List<Users> findByEmail(String email);

	Boolean existsByEmail(String email);
	
	@Query(value = "select id from users where email=:email" , nativeQuery = true)
	UUID getIdByEmail(@Param("email")  String email);
	
	@Query(value = "select * from users where email=:email" , nativeQuery = true)
	Users getUserByEmail(@Param("email")  String email);

}
