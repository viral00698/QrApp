package com.QrApplication.Repository;

import java.util.Date;
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

	List<Orders> findByOrderStatusAndVendorId(OrderStatus orderStatus , UUID vendorId);
	
	boolean existsByOrderId(UUID id);

	@Modifying
	@Transactional
	@Query("UPDATE Orders o SET o.orderStatus=:orderStatus WHERE o.orderId =:orderId")
	int updateStatus(@Param("orderId") UUID orderId, @Param("orderStatus") OrderStatus orderStatus);

	List<Orders> findByCustomerUUID(UUID customerId);
	
//	@Query("SELECT o FROM Orders o WHERE (o.orderStatus = CONFIRMED OR o.orderStatus=PREPARING) AND FUNCTION('DATE', o.orderAt) =:date AND vendorId = :id")
	@Query("SELECT o FROM Orders o WHERE (o.orderStatus = 'CONFIRMED' OR o.orderStatus = 'PREPARING') AND o.vendorId = :id")
	List<Orders> getOngoinOrder(@Param("id") UUID id);

	
	@Query("SELECT COUNT(o.orderId) AS totalOrderCount, " +
		       "SUM(o.totelAmount) AS totalRevenue, " +
		       "COUNT(CASE WHEN o.orderStatus = CONFIRMED THEN 1 END) AS totalActiveOrderCount " +
		       "FROM Orders o " +
		       "WHERE o.vendorId = :vendorId " +
		       "AND o.orderAt BETWEEN :startDate AND :endDate")
		Object findVendorOrderStatistics(@Param("vendorId") UUID vendorId, 
		                                 @Param("startDate") Date startDate, 
		                                 @Param("endDate") Date endDate);

	
}
