package com.QrApplication.PlaceOrderObsever;

import java.util.HashMap;
import java.util.UUID;

public interface SubjectInterface {
		
	public static final HashMap<UUID, String> lsit = new HashMap<UUID, String>();
	
	void upadte(Boolean aviliblity); 
	
	void add(String item);
	
	void notifyObsevers();
}
