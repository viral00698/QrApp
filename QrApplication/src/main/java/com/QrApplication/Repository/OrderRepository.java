package com.QrApplication.Repository;

import java.time.LocalDateTime;
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
public interface OrderRepository extends JpaRepository<Orders, UUID> {

	List<Orders> findByOrderStatusAndVendorId(OrderStatus orderStatus, UUID vendorId);

	boolean existsByOrderId(UUID id);

	@Modifying
	@Transactional
	@Query("UPDATE Orders o SET o.orderStatus=:orderStatus WHERE o.orderId =:orderId")
	int updateStatus(@Param("orderId") UUID orderId, @Param("orderStatus") OrderStatus orderStatus);

	List<Orders> findByCustomerUUID(UUID customerId);

//	@Query("SELECT o FROM Orders o WHERE (o.orderStatus = CONFIRMED OR o.orderStatus=PREPARING OR o.orderStatus=ONGOING) AND FUNCTION('DATE', o.orderAt) =:date AND vendorId = :id")
	@Query("SELECT o FROM Orders o WHERE (o.orderStatus = 'CONFIRMED' OR o.orderStatus = 'PREPARING' OR o.orderStatus = 'ONGOING') AND o.vendorId = :id")
	List<Orders> getOngoinOrder(@Param("id") UUID id);

	@Query("SELECT COUNT(o.orderId) AS totalOrderCount, " + "SUM(o.totelAmount) AS totalRevenue, "
			+ "COUNT(CASE  WHEN o.orderStatus IN ('CONFIRMED', 'ONGOING')  THEN 1 END) AS totalActiveOrderCount "
			+ "FROM Orders o " + "WHERE o.vendorId = :vendorId " + "AND o.orderAt BETWEEN :startDate AND :endDate")
	Object findVendorOrderStatistics(@Param("vendorId") UUID vendorId, @Param("startDate") Date startDate,
			@Param("endDate") Date endDate);

	@Query("SELECT o FROM Orders o JOIN o.tableOrder t WHERE t.tableId = :tableId AND t.tableStatus='BOOKED' AND  o.vendorId=:vendorId AND o.orderStatus = 'ONGOING'")
	List<Orders> getTableOnGoingOrder(@Param("tableId") UUID tableId, @Param("vendorId") UUID vendorId);

	List<Orders> findByVendorId(UUID fromString);

	@Modifying
	@Transactional
	@Query("UPDATE Orders o SET o.orderStatus = :orderStatus, o.txid = :txn_id, o.paymentStatus = :pay_status, o.paymentDetail = :payment_details WHERE o.orderId = :orderId")
	int updateOnlineOrderStatus(@Param("orderId") UUID orderId, @Param("orderStatus") OrderStatus orderStatus,
			@Param("txn_id") String txn_id, @Param("pay_status") PaymentStatus pay_status,
			@Param("payment_details") PaymentDetail payment_details);

	@Query("SELECT o FROM Orders o JOIN FETCH o.orderDetails WHERE o.token_no = :token AND o.vendorId=:vedeorId")
	Orders getOrdersByTokenAndVendor(@Param("vedeorId") UUID vedeorId, @Param("token") String token);

	@Query("SELECT o FROM Orders o JOIN FETCH o.orderDetails WHERE o.vendorId = :vedeorId AND o.orderId=:orderId")
	Orders getOrdersByOrderIdAndVendorId(@Param("vedeorId") UUID vedeorId, @Param("orderId") UUID orderId);

	@Query("SELECT o FROM Orders o JOIN FETCH o.orderDetails WHERE o.vendorId = :vedeorId AND o.orderAt >= :last24Hours")
	List<Orders> getLastTwoDayOrder(@Param("vedeorId") UUID vedeorId, @Param("last24Hours") LocalDateTime last24Hours);

//	@Query(value = "SELECT order_status ,DATE(order_at) AS order_date, COUNT(order_id) AS total_orders " +
//            "FROM public.orders " +
//            "WHERE vendor_id = :vid " +
//            "GROUP BY DATE(order_at) " +
//            "ORDER BY order_date", nativeQuery = true)

	@Query(value = "SELECT order_status,   order_at AS order_datetime, COUNT(order_id) AS total_orders FROM public.orders WHERE vendor_id = :vid GROUP BY order_at , order_status ORDER BY order_datetime", nativeQuery = true)
	List<Object[]> countOrdersGroupByDay(@Param("vid") UUID vid);

	@Query(value = "SELECT customer_mobile_no, DATE(order_at) AS order_date, COUNT(*) AS order_count FROM public.orders WHERE vendor_id = :vid  GROUP BY customer_mobile_no, DATE(order_at) HAVING COUNT(*) > 0", nativeQuery = true)
	List<Object[]> customerInsides(@Param("vid") UUID vid);

	@Query(value = """
			SELECT
				DATE(o.order_at) AS order_date,
			    od.food_category,
			    COUNT(*) AS order_count,
			    SUM(od.amount * od.quntity) AS total_revenue
			FROM
			    order_details od
			JOIN
			    orders o ON od.order_id = o.order_id
			WHERE
			    o.vendor_id = :vid
			GROUP BY
			    od.food_category, DATE(o.order_at)
			HAVING
			    COUNT(*) > 0
			""", nativeQuery = true)
	List<Object[]> revenueByFoodCategory(UUID vid);

	@Query(value = """
			    SELECT
			      DATE(order_at) AS order_date,
			      SUM(totel_amount) as totel_amount,
			      COUNT(order_id) AS total_orders,
			      COUNT(CASE WHEN order_status = 'ONGOING' THEN 1 END) AS active_orders
			  FROM public.orders
			  WHERE vendor_id = :vid
			  GROUP BY DATE(order_at),totel_amount
			  HAVING COUNT(*) > 0
			""", nativeQuery = true)
	List<Object[]> orderStatictics(@Param("vid") UUID vid);

	@Query(value = """
		    SELECT
		        od.item_name,
		        SUM(od.quntity) AS total_quantity,
		        SUM(od.amount * od.quntity) AS total_revenue
		    FROM order_details od
		    JOIN orders o ON od.order_id = o.order_id
		    WHERE o.vendor_id = :vid
		      AND o.order_at >= CURRENT_DATE - INTERVAL '35 days'
		    GROUP BY od.item_name
		    ORDER BY total_quantity DESC
		    LIMIT 13
		""", nativeQuery = true)	List<Object[]> getTopSellingItems(@Param("vid") UUID vid);

	@Query(value = """
		    SELECT
		        od.item_name,
		        SUM(od.quntity) AS total_quantity,
		        SUM(od.amount * od.quntity) AS total_revenue
		    FROM order_details od
		    JOIN orders o ON od.order_id = o.order_id
		    WHERE o.vendor_id = :vid
		      AND o.order_at >= CURRENT_DATE - INTERVAL '35 days'
		    GROUP BY od.item_name
		    ORDER BY total_quantity ASC
		    LIMIT 13
		""", nativeQuery = true)
	List<Object[]> getLowestSellingItems(@Param("vid") UUID vid);

}
