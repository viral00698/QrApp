package com.QrApplication.AuthSecret;

import org.springframework.http.HttpStatus;
import com.QrApplication.Enum.RequestStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Data
public class ResponseType {

	private String message;
	private HttpStatus statusCode;
	private RequestStatus status;
	
	@JsonBackReference
	private Object object;

	public static ResponseType ResponseGenerator(RequestStatus requestStatus, String message, Object object) {
		ResponseType obj = new ResponseType();
		obj.setStatus(requestStatus);
		obj.setObject(object);
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
		obj.setObject(object);
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

}
