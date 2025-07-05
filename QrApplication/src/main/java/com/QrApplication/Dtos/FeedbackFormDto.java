package com.QrApplication.Dtos;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class FeedbackFormDto {

	  private Long id;
	  private LocalDateTime createAt;
	  private UUID orderId;
	  private UUID vendorId;
	  private Boolean status;
	  private List<FeedbackAnswerDto> answers;
}
