package com.QrApplication.Entity;

import java.util.UUID;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID productId;

	@Column(nullable = false)
	private String itemName;

	@Column(nullable = false)
	private Double amount;

	
	private Integer quantity = 0;
	
	@Column
	private Integer gram = 0;

	@Column
	private Integer totalGram = 0;
	
	private Boolean jain;
	
	@Column
	private Boolean vegNonVeg;

	@Column
	private Integer totalQuantity = 0;
	private String description;

	@Column(nullable = false)
	private Boolean status;
	
	private String image = "Image";
	
	@Transient
	private Integer itemQty = 0; // for item increment only
		
//	@JsonBackReference(value = "product-vendor")
	@ManyToOne
	@JoinColumn(name = "vendor_id")
	private Vendor vendor;
	
//	@ManyToOne
//	@JoinColumn
//	@JsonIgnore
//	private Users users;

//	@Override
//	public String toString() {
//		return "Product [productId=" + productId + ", itemName=" + itemName + ", quantity=" + quantity + ", gram="
//				+ gram + ", totalGram=" + totalGram + ", totalQuantity=" + totalQuantity + ", description="
//				+ description + ", status=" + status + ", image=" + image + "]";
//	}

}
