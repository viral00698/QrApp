package com.QrApplication.Entity;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
public class FeedbackForm {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.DATE)
	private Date createAt;

	@Column(nullable = false)
	private UUID orderId;

	@ManyToOne
	@JoinColumn(name = "vendorId")
	private Vendor vendor;

	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@OneToMany(mappedBy = "feedbackForm", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<FeedbackAnswer> answers;

	@PrePersist
	protected void onCreate() {
		this.createAt = new Date();
	}
}
