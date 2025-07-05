package com.QrApplication.Service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.QrApplication.AuthSecret.ResponseType;
import com.QrApplication.Dtos.FeedbackFormDto;
import com.QrApplication.Dtos.QuestionDto;
import com.QrApplication.Entity.FeedbackForm;
import com.QrApplication.Entity.Question;
import com.QrApplication.Enum.RequestStatus;
import com.QrApplication.Mapper.FeedbackFormMapper;
import com.QrApplication.Mapper.QuestionMapper;
import com.QrApplication.Repository.FeedbackAnswerRepo;
import com.QrApplication.Repository.FeedbackFormRepo;
import com.QrApplication.Repository.QuestionRepo;

@Service
public class QuestionService {

	@Autowired
	private QuestionMapper questionMapper; 
	
	@Autowired
	private QuestionRepo questionRepo;
	
	@Autowired
	private FeedbackFormMapper feedbackFormMapper;
	
	@Autowired
	private FeedbackFormRepo feedbackFormRepo;
	
	@Autowired
	private FeedbackAnswerRepo feedbackAnswerRepo;
	
	
	public ResponseType addQuestion(QuestionDto questionDto) {
		
		try {
			Question res =  questionMapper.toEntity(questionDto);
			res.setIsActive(false);
			questionRepo.save(res);
			return ResponseType.ResponseGenerator(RequestStatus.success, "Question added successfully");
		} catch (Exception e) {
			return ResponseType.ResponseGenerator(RequestStatus.failure, e.getMessage());
		}
		
	}


	public ResponseType getActiveQuestion(UUID vid) {
		try {
			List<Question> res = questionRepo.findRandom5ByVendorIdAndIsActiveTrue(vid);
			if(res.isEmpty())
				return ResponseType.ResponseGenerator(RequestStatus.failure, "Record is Empty");
			return ResponseType.ResponseGenerator(RequestStatus.success, res);
		} catch (Exception e) {
			return ResponseType.ResponseGenerator(RequestStatus.failure, e.getMessage());
		}
		
	}


	public ResponseType saveFeedBack(FeedbackFormDto feedbackFormDto) {
		
		try {
			
			FeedbackForm feedbackForm =  feedbackFormMapper.toEntity(feedbackFormDto);
			feedbackFormMapper.mapAnswersFromDto(feedbackForm, feedbackFormDto, feedbackFormDto.getAnswers());
			
			feedbackFormRepo.save(feedbackForm);
			
			return ResponseType.ResponseGenerator(RequestStatus.success, "Thank you! Your feedback has been received.");
		
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseType.ResponseGenerator(RequestStatus.failure, "Get Error while receiveing feedback");
		}
	}


	public ResponseType getFeedBackByVendor(String vid) {
		
		try {
			
			List<Object[]> res = feedbackFormRepo.getFeedbackByVendor(UUID.fromString(vid));
			return ResponseType.ResponseGenerator(RequestStatus.success, res);
			
		} catch (Exception e) {
			return ResponseType.ResponseGenerator(RequestStatus.failure, "Get Error while get feedback");
		}
		
	}


	public ResponseType feedbackQuestionDisable(FeedbackFormDto feedbackFormDto) {
		try {
			
			int active =  questionRepo.setQuestionStatusBy(feedbackFormDto.getStatus() , feedbackFormDto.getId(),feedbackFormDto.getVendorId());
			if(active > 0) {
				return ResponseType.ResponseGenerator(RequestStatus.success, "Question status Updated");
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseType.ResponseGenerator(RequestStatus.failure, "Getting Error while set Qusetion status");
		}
		return ResponseType.ResponseGenerator(RequestStatus.failure, "Invalid Request");
	}

}
