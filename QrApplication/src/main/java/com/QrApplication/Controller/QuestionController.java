package com.QrApplication.Controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.QrApplication.AuthSecret.ResponseType;
import com.QrApplication.Dtos.FeedbackFormDto;
import com.QrApplication.Dtos.QuestionDto;
import com.QrApplication.Service.QuestionService;

@RestController
@RequestMapping("question")
public class QuestionController {

	@Autowired
	private QuestionService questionService;
	
	@PostMapping("save")
	ResponseType addQuestion(@RequestBody QuestionDto questionDto){
		return questionService.addQuestion(questionDto);
	}
	
	@GetMapping("getActiveQuestion/{id}")
	ResponseType addQuestion(@PathVariable("id") String id){
		return questionService.getActiveQuestion(UUID.fromString(id));
	}
	
	@PostMapping("saveFeedBack")
	ResponseType saveFeedBack(@RequestBody FeedbackFormDto feedbackFormDto){
		return questionService.saveFeedBack(feedbackFormDto);
	}
	
	@GetMapping("getFeedback/{vid}")
	ResponseType getFeedbackByVendor(@PathVariable("vid") String vid) {
		return questionService.getFeedBackByVendor(vid);
	}
	
	@PostMapping("feedbackQuestionDisable")
	ResponseType feedbackQuestionDisable(@RequestBody FeedbackFormDto feedbackFormDto) {
		return questionService.feedbackQuestionDisable(feedbackFormDto);
	}
	
	
}
