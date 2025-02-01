package com.QrApplication.AuthSecret;

import org.springframework.http.HttpStatus;
import com.QrApplication.Enum.RequestStatus;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Data
public class ResponseType {

	private String message;
	private HttpStatus statusCode;
	private RequestStatus status;
	
	@JsonManagedReference
	private Object data;

	public static ResponseType ResponseGenerator(RequestStatus requestStatus, String message, Object object) {
		ResponseType obj = new ResponseType();
		obj.setStatus(requestStatus);
		obj.setData(object);
		obj.setMessage(message);

		return obj;
	}
	

	public static ResponseType ResponseGenerator(RequestStatus requestStatus, String message, HttpStatus status) {
		ResponseType obj = new ResponseType();
		obj.setStatus(requestStatus);
		obj.setStatusCode(status);
		obj.setMessage(message);

		return obj;
	}

	public static ResponseType ResponseGenerator(RequestStatus requestStatus, String message, Object object,
			HttpStatus status) {
		ResponseType obj = new ResponseType();
		obj.setStatus(requestStatus);
		obj.setData(object);
		obj.setMessage(message);
		obj.setStatusCode(status);

		return obj;
	}
	
	public static ResponseType ResponseGenerator(RequestStatus requestStatus, String message) {
		ResponseType obj = new ResponseType();
		obj.setStatus(requestStatus);
		obj.setMessage(message);
		return obj;
	}
	
	public static ResponseType ResponseGenerator(RequestStatus requestStatus, Object data) {
		ResponseType obj = new ResponseType();
		obj.setStatus(requestStatus);
		obj.setData(data);;
		return obj;
	}
	
	
	

}
