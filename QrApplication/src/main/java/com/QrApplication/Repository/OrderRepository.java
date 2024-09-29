package com.QrApplication.Repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.QrApplication.Entity.Orders;
import com.QrApplication.Enum.OrderStatus;

@Repository
public interface OrderRepository extends JpaRepository<Orders, UUID>{

	List<Orders> findByOrderStatus(OrderStatus orderStatus);
	
	boolean existsByOrderId(UUID id);

	@Modifying
	@Transactional
	@Query("UPDATE Orders o SET o.orderStatus=:orderStatus WHERE o.orderId =:orderId")
	int updateStatus(@Param("orderId") UUID orderId, @Param("orderStatus") OrderStatus orderStatus);

}
