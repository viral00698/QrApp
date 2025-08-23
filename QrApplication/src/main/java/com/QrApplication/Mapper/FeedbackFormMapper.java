package com.QrApplication.Mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.QrApplication.Dtos.FeedbackAnswerDto;
import com.QrApplication.Dtos.FeedbackFormDto;
import com.QrApplication.Entity.FeedbackAnswer;
import com.QrApplication.Entity.FeedbackForm;
import com.QrApplication.Entity.Question;

@Mapper(componentModel = "spring", uses = { FeedbackAnswerMapper.class })
public interface FeedbackFormMapper {

	@Mapping(target = "status", ignore=true)
	@Mapping(source = "vendor.vendorId", target = "vendorId")
	FeedbackFormDto toDto(FeedbackForm form);

	@Mapping(source = "vendorId", target = "vendor.vendorId")
	@Mapping(target = "answers", ignore = true) // will be set manually after resolving questions
	FeedbackForm toEntity(FeedbackFormDto dto);

	@AfterMapping
	default void mapAnswers(@MappingTarget FeedbackForm form, FeedbackFormDto dto, @Context List<Question> questions) {
		List<FeedbackAnswer> answers = dto.getAnswers().stream().map(a -> {
			FeedbackAnswer answer = new FeedbackAnswer();
			answer.setRating(a.getRating());

			Question question = questions.stream().filter(q -> q.getId().equals(a.getQuestionId())).findFirst()
					.orElse(null);

			answer.setQuestion(question);
			answer.setFeedbackForm(form);
			return answer;
		}).toList();

		form.setAnswers(Set.copyOf(answers));
	}

	@AfterMapping
	default void mapAnswersFromDto(@MappingTarget FeedbackForm form, FeedbackFormDto dto,
			@Context List<FeedbackAnswerDto> questionEntities) {
			Set<FeedbackAnswer> answers = dto.getAnswers().stream().map(a -> {
			FeedbackAnswer answer = new FeedbackAnswer();
			answer.setRating(a.getRating());

			Question question = new Question();
			if(a.getQuestionId()!=null) {
				question.setId(a.getQuestionId());
			}
			
			
			answer.setQuestion(question);
			answer.setFeedbackForm(form);
			return answer;
		}).collect(Collectors.toSet());

		form.setAnswers(answers);
	}
	


}
