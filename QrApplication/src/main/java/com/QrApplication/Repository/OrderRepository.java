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
import com.QrApplication.Entity.PaymentDetail;
import com.QrApplication.Enum.OrderStatus;
import com.QrApplication.Enum.PaymentStatus;

@Repository
public interface OrderRepository extends JpaRepository<Orders, UUID>{

	List<Orders> findByOrderStatusAndVendorId(OrderStatus orderStatus , UUID vendorId);
	
	boolean existsByOrderId(UUID id);

	@Modifying
	@Transactional
	@Query("UPDATE Orders o SET o.orderStatus=:orderStatus WHERE o.orderId =:orderId")
	int updateStatus(@Param("orderId") UUID orderId, @Param("orderStatus") OrderStatus orderStatus);

	List<Orders> findByCustomerUUID(UUID customerId);
	
//	@Query("SELECT o FROM Orders o WHERE (o.orderStatus = CONFIRMED OR o.orderStatus=PREPARING OR o.orderStatus=ONGOING) AND FUNCTION('DATE', o.orderAt) =:date AND vendorId = :id")
	@Query("SELECT o FROM Orders o WHERE (o.orderStatus = 'CONFIRMED' OR o.orderStatus = 'PREPARING' OR o.orderStatus = 'ONGOING') AND o.vendorId = :id")
	List<Orders> getOngoinOrder(@Param("id") UUID id);

	
	@Query("SELECT COUNT(o.orderId) AS totalOrderCount, " +
		       "SUM(o.totelAmount) AS totalRevenue, " +
		       "COUNT(CASE  WHEN o.orderStatus IN ('CONFIRMED', 'ONGOING')  THEN 1 END) AS totalActiveOrderCount " +
		       "FROM Orders o " +
		       "WHERE o.vendorId = :vendorId " +
		       "AND o.orderAt BETWEEN :startDate AND :endDate")
		Object findVendorOrderStatistics(@Param("vendorId") UUID vendorId, 
		                                 @Param("startDate") Date startDate, 
		                                 @Param("endDate") Date endDate);

	
	@Query("SELECT o FROM Orders o JOIN o.tableOrder t WHERE t.tableId = :tableId AND t.tableStatus='BOOKED' AND  o.vendorId=:vendorId AND o.orderStatus = 'ONGOING'")
	List<Orders> getTableOnGoingOrder(@Param("tableId") UUID tableId , @Param("vendorId") UUID vendorId);

	List<Orders> findByVendorId(UUID fromString);
	
	@Modifying
	@Transactional
	@Query("UPDATE Orders o SET o.orderStatus = :orderStatus, o.txid = :txn_id, o.paymentStatus = :pay_status, o.paymentDetail = :payment_details WHERE o.orderId = :orderId")
	int updateOnlineOrderStatus(
	    @Param("orderId") UUID orderId, 
	    @Param("orderStatus") OrderStatus orderStatus, 
	    @Param("txn_id") String txn_id, 
	    @Param("pay_status") PaymentStatus pay_status,
	    @Param("payment_details") PaymentDetail payment_details
	);

	
}
