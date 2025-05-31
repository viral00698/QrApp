package com.QrApplication.Service;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

@Service
public class TwilioSmsInvoice {
    // Your Twilio Account SID and Auth Token
    public static final String ACCOUNT_SID = "ACb4f93a9b54c23a95f284a84a7625db5c";
    public static final String AUTH_TOKEN = "8d8eb1422eff42726ad513a8932ca40f";

    private static final Logger logger = LoggerFactory.getLogger(TwilioSmsInvoice.class);
    
    public void sendSms(UUID uuid , String customerMobile){
    	
    	try {
    		  Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

    	        String toPhoneNumber = "+91"+customerMobile; // Customer's phone number
    	        String fromPhoneNumber = "+19713858539"; // Your Twilio number
//    	        String invoiceLink = "http://192.168.196.204:8080/invoice/"+uuid;
    	        String invoiceLink = "https://back.vitts.in/invoice/"+uuid; // Invoice link

    	        Message message = Message.creator(
    	                new com.twilio.type.PhoneNumber(toPhoneNumber),
    	                new com.twilio.type.PhoneNumber(fromPhoneNumber),
    	                "Your invoice is ready. View it here: " + invoiceLink)
    	            .create();
    	        logger.info("Invoice SMS sent to {}. Message SID: {}", toPhoneNumber, message.getSid());
    	        System.out.println("Invoice sent! Message SID: " + message.getSid());
		} catch (Exception e) {
            logger.error("Failed to send SMS to {} for invoice {}: {}", customerMobile, uuid, e.getMessage(), e);
			// TODO: handle exception
		}
    	
      
    }
}