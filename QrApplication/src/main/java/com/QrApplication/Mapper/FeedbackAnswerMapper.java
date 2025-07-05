package com.QrApplication.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.QrApplication.Dtos.FeedbackAnswerDto;
import com.QrApplication.Entity.FeedbackAnswer;

@Mapper(componentModel = "spring")
public interface FeedbackAnswerMapper {
	
	 @Mapping(source = "question.id", target = "questionId")
	 FeedbackAnswerDto toDto(FeedbackAnswer answer);

}
