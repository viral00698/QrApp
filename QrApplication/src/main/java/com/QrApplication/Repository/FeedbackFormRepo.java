package com.QrApplication.Repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.QrApplication.Entity.FeedbackForm;

public interface FeedbackFormRepo extends JpaRepository<FeedbackForm, Long> {

	
	@Query(value = """
		    SELECT 
		  
		        Q.text AS question_text, 
		        COUNT(A.id) AS total_responses, 
		        MIN(A.rating) AS worst_rating, 
		        MAX(A.rating) AS best_rating, 
		        ROUND(AVG(A.rating), 2) AS average_rating, 
		         Q.id AS question_id,
		         Q.is_active as status
		    FROM 
		        question Q 
		    JOIN 
		        feedback_answer A ON A.question_id = Q.id 
		    JOIN 
		        feedback_form F ON F.id = A.feedback_id 
		    WHERE 
		        F.vendor_id = :vid 
		    GROUP BY 
		        Q.id, Q.text 
		    ORDER BY 
		        Q.id
		""", nativeQuery = true)
		List<Object[]> getFeedbackByVendor(@Param("vid") UUID vendorId);


}
