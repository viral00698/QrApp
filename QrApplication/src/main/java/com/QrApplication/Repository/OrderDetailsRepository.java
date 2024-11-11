package com.QrApplication.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.json.JSONObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.QrApplication.Entity.OrderDetails;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, UUID>{

	
	@Query("SELECT od.itemName, COUNT(od) AS orderCount " +
		       "FROM OrderDetails od JOIN od.orderId o " +
		       "WHERE o.vendorId = :vendorId " +
		       "AND o.orderAt BETWEEN :startDate AND :endDate " +
		       "GROUP BY od.itemName " +
		       "ORDER BY orderCount DESC")
		List<Object> findTop10MostOrderedItemsByVendorAndDateRange(
		    @Param("vendorId") UUID vendorId,
		    @Param("startDate") Date startDate,
		    @Param("endDate") Date endDate,
		    Pageable pageable);
}
