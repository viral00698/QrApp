package com.QrApplication.Repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.QrApplication.Entity.TableOrder;
import com.QrApplication.Enum.TableStatus;

@Repository
public interface TableOrderRepository extends JpaRepository<TableOrder, UUID> {

//	List<TableOrder> findByVendorId(UUID vendorId);
	List<TableOrder> findByVendorIdAndTableStatusNot(UUID vendorId, TableStatus tableStatus);


	@Modifying
	@Transactional
	@Query("DELETE FROM TableOrder t WHERE t.tableId = :tableId")
	int deleteTableByVendorId(@Param("tableId") UUID tableId);
	
	@Modifying
	@Transactional
	@Query("UPDATE TableOrder t SET t.tableStatus = :status WHERE t.tableId = :tableId AND t.vendorId = :vendorId")
	int updateTableStatus(@Param("status") TableStatus status, @Param("tableId") UUID tableId, @Param("vendorId") UUID vendorId);

	TableOrder findByTableId(UUID tableId);

}
