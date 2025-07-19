package com.QrApplication.Entity;

import java.util.UUID;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class UserDocuments {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID docId;
	
	private String aadharNo;
	private String aadharDoc;
	
	private String panNo;
	private String pandDoc;
	
	private String fssiNo;
	private String fssiDoc;
	
	private String gstNo;
	private String gstDoc;
	
	private UUID userId;  //it is point vendor or user/employee
	
}
