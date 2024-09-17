package com.QrApplication.SocketController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testSocketController {
	
	 @Autowired
	 private  SimpMessagingTemplate messagingTemplate;

	    @MessageMapping("/sendMessage")
	    @SendTo("/topic/messages")
	    public String send(String message) {
		 System.err.println("Hello Test succes");
		 System.err.println(message);
	        return message;
	    }
	 public void sendMessageToTopic(String message) {
	        messagingTemplate.convertAndSend("/topic/messages", message);
	  }
}
