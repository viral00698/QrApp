package com.QrApplication.AuthSecret;


import org.springframework.http.HttpStatus;

import com.QrApplication.Enum.RequestStatus;

import lombok.Data;

@Data
public class ResponseType<T>{
		
	private String message;
	private HttpStatus statusCode;
	private RequestStatus requestStatus;
	private T data;
	

    public  ResponseType<T> ResponseGenerator(HttpStatus status, String message, T data) {
        this.statusCode = status;
        this.message = message;
        this.data = data;
        
        return this;
    }
    
    public  ResponseType<T> ResponseGenerator(HttpStatus status, String message) {
        this.statusCode = status;
        this.message = message;
        
        return this;
    }
    
    public  ResponseType<T> ResponseGenerator(HttpStatus status , RequestStatus requestStatus) {
        this.statusCode = status;
        this.requestStatus = requestStatus;
       
        return this;
    }
    
    public  ResponseType<T> ResponseGenerator(HttpStatus status , RequestStatus requestStatus , String msg) {
        this.statusCode = status;
        this.requestStatus = requestStatus;
        this.message  = msg;
       
        return this;
    }
    
    
    public  ResponseType<T> ResponseGenerator(HttpStatus status) {
        this.statusCode = status;
        
        return this;
    }
    


}
