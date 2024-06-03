package com.QrApplication.Services;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.QrApplication.Entity.Users;

public interface UserRepos extends JpaRepository<Users, UUID>{
	
	public List<Users> findByEmail(String email);
}
