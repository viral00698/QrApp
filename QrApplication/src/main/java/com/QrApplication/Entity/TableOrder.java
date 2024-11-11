package com.QrApplication.Entity;

import java.util.UUID;

import com.QrApplication.Enum.TableStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class TableOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID tableId;
	
	@Column(nullable = false)
	private String tableName;
	
	@Column(nullable = false)
	private UUID vendorId;
	
//	@Column(nullable = false)
//	private String size;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private TableStatus tableStatus;
	
//	@OneToOne(fetch = FetchType.EAGER)
//	private Orders orders;
	
}
