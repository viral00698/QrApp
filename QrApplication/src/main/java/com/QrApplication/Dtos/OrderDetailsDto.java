package com.QrApplication.Dtos;

import java.util.UUID;

import com.QrApplication.Enum.FoodCategory;
import com.QrApplication.Enum.OfferType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailsDto {

    private UUID orderDetailsId;

    private String itemName;

    private UUID productId;

    private FoodCategory foodCategory;

    private Double amount;

    private Boolean isJain;

    private Integer quntity;

    private UUID orderId;

    private UUID offerId;

    private OfferType offerType;

    private Boolean offerApplied;

    private Boolean isDelivered;
}

