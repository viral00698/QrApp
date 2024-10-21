package com.QrApplication.Dtos;

import java.util.Date;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class StatisticsDto {

//	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date startDate;

//	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date endDate;
	private UUID venderId;
}
