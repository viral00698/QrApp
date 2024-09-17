package com.QrApplication.Repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.QrApplication.Entity.Orders;
import com.QrApplication.Enum.OrderStatus;

@Repository
public interface OrderRepository extends JpaRepository<Orders, UUID>{

	List<Orders> findByOrderStatus(OrderStatus orderStatus);
}
