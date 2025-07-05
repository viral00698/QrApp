package com.QrApplication.Dtos;

import java.util.UUID;

import lombok.Data;

@Data
public class QuestionDto {
	
	  private Long id;
	  private String text;
	  private Boolean isActive;
	  private UUID vendorId;

}
