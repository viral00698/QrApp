package com.QrApplication.Dtos;

import java.util.UUID;

import com.QrApplication.Enum.FoodCategory;
import lombok.Data;

@Data
public class ProductDto {

    private UUID productId;
    private String itemName;
    private Double amount;
    private Integer quantity;
    private Integer gram;
    private Integer totalGram;
    private Boolean jain;
    private FoodCategory foodCategory;
    private Boolean vegNonVeg;
    private Integer totalQuantity;
    private String description;
    private Boolean status;
    private UUID offerId;   // only the ID instead of the full Offer entity
    private String image;
    private Integer itemQty;
    private UUID vendorId;  // only the ID instead of the full Vendor entity
}
