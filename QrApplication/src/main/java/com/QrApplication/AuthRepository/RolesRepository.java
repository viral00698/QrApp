package com.QrApplication.AuthRepository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.QrApplication.Entity.Roles;

@Repository
public interface RolesRepository extends JpaRepository<Roles, UUID>{
	
}
