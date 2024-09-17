package com.QrApplication.Repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.QrApplication.Entity.Product;
import com.QrApplication.Entity.Users;

@EnableJpaRepositories
@Repository
public interface ProductRepository extends JpaRepository<Product, UUID>{
		
	 	@Query("SELECT p FROM Product p WHERE p.productId IN :ids AND p.status=true")
		public List<Product> checkAviliblityInDb(@Param("ids") List<UUID> ids);
	 	
	 	public List<Product> findByUsers(Users users);
}
