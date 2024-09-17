package com.QrApplication.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.QrApplication.Entity.OrderDetails;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, UUID>{

}
