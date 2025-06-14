package com.QrApplication.Enum;

public enum OfferType {
	
	   // ✅ Standard Discounts
    FLAT_DISCOUNT,       // ₹50 off or 10% off on a single item or cart
    ITEM_DISCOUNT,       // Discount on specific item(s)
    
    // ✅ Quantity-Based Offers
    BOGO,                // Buy One Get One Free
    BUY_X_GET_Y,         // Buy 2 Get 1 Free
    BULK_DISCOUNT,       // Discount on bulk quantity (e.g., 3+ items)

    // ✅ Combo Deals
    COMBO,               // Bundle of items at a special price
    
    // ✅ Conditional Offers
    MIN_ORDER_DISCOUNT,  // Discount if min cart value met
    TIME_BASED,          // Only valid in certain hours (e.g., Happy Hours)
    FIRST_ORDER,         // For new customers only
    RETURNING_CUSTOMER,  // Only for repeat users

    // ✅ Payment-based
    PAYMENT_METHOD,      // Discount based on payment type (e.g., UPI, Wallet)

    // ✅ Loyalty or Membership
    LOYALTY_DISCOUNT,    // Discount for members / loyalty users
    COUPON_CODE,         // Promo/coupon based discount

    // ✅ Free Items
    FREE_ITEM,           // Free addon or item with order
    
    // ✅ Delivery-specific
    FREE_DELIVERY        // Free delivery instead of %/₹ discount

}
