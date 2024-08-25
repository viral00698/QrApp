package com.QrApplication.AuthService;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.QrApplication.AuthSecret.ResponseType;
import com.QrApplication.Enum.RequestStatus;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAuthenticationEntryPoint  implements AuthenticationEntryPoint {

	private final ObjectMapper objectMapper = new ObjectMapper();
	 
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			org.springframework.security.core.AuthenticationException authException)
			throws IOException, ServletException {

//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			response.reset();
//        	response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        	response.getWriter().write("Unauthorized: Authentication is required");
//        	response.getWriter().flush();
		
		 	response.setContentType(MediaType.APPLICATION_JSON_VALUE);
	        response.setCharacterEncoding("UTF-8");

	        // Set the response status code
	        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

	        // Create a JSON object for the error message
//	        String jsonResponse = "{ \"error\": \"Unauthorized: Authentication is required\" }";
	        String jsonResponse= objectMapper.writeValueAsString(new ResponseType<String>().ResponseGenerator(HttpStatus.BAD_REQUEST , RequestStatus.failure, "Unauthorized: Authentication is required"));
	        // Write the JSON response
	        response.getWriter().write(jsonResponse);
	        response.getWriter().flush();
	}

}
