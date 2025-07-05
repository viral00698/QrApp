package com.QrApplication.Repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.QrApplication.Entity.Question;

import jakarta.transaction.Transactional;

@Repository
@EnableTransactionManagement
public interface QuestionRepo extends JpaRepository<Question, Long>{
	
//	List<Question> findByVendor_VendorIdAndIsActiveTrue(UUID vendorId);
	@Query(value = """
		    SELECT * FROM question 
		    WHERE is_active = true 
		      AND vendor_id = :vendorId 
		    ORDER BY RANDOM() 
		    LIMIT 5
		    """, nativeQuery = true)
	List<Question> findRandom5ByVendorIdAndIsActiveTrue(@Param("vendorId") UUID vendorId);
	
	@Transactional
	@Modifying
	@Query("UPDATE Question q SET q.isActive = :status WHERE q.id = :id AND q.vendor.vendorId = :vendorId")
	int setQuestionStatusBy(@Param("status") Boolean status , @Param("id") Long id, @Param("vendorId") UUID vendorId);
;
}
