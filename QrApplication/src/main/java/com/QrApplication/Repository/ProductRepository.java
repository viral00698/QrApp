package com.QrApplication.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.QrApplication.Entity.Product;

@EnableJpaRepositories
public interface ProductRepository extends JpaRepository<Product, UUID>{

}
