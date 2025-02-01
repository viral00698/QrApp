package com.QrApplication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.QrApplication.Entity.ProductList;

@Repository
public interface ProductListRepos extends JpaRepository<ProductList, Long>{

}
