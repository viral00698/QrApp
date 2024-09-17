package com.QrApplication.Interface;

import java.util.HashMap;
import java.util.UUID;

public interface VendorMapSubject {
	
	public int getVenderMapFromDB();
	public String getVenderStorenameByUUID(UUID id);
}
