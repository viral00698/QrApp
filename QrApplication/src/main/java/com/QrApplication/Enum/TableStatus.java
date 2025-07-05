package com.QrApplication.Enum;

public enum TableStatus {

	 	AVAILABLE,    // The table is currently available for booking
	    
	    BOOKED,       // The table has been booked by a customer but not yet occupied
	    
	    OCCUPIED,     // The table is currently occupied by the customer
	    
	    IN_USE,       // The table is in use, and the order is being processed
	    
	    WAITING,      // The table is reserved for a customer, but they have not yet arrived
	    
	    CLOSED,       // The table booking is complete; the customer has left, and the order is closed
	    
	    CANCELLED,    // The table booking has been canceled by either the customer or the restaurant
	    
	    CLEANING,     // The table is being cleaned and is temporarily unavailable for booking
	    
	    RESERVED,      // The table is reserved for a specific time but not yet in use
	    
	    ONGOING,      // The table is reserved for a specific time but not yet in use
	    
	    REMOVE
	
}
