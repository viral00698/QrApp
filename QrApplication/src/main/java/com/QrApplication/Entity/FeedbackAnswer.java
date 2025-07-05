package com.QrApplication.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
public class FeedbackAnswer {
	
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private Integer rating;

	    @ManyToOne
	    @JoinColumn(name = "question_id")
	    private Question question;

	    @ToString.Exclude
	    @EqualsAndHashCode.Exclude
	    @ManyToOne
	    @JoinColumn(name = "feedbackId")
	    private FeedbackForm feedbackForm;

}
