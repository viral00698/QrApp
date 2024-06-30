package com.QrApplication.Services;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
			
		if (authentication == null) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Authentication object is null");
            response.getWriter().flush();
            return;
        }

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write("Authentication Successful!");
        System.err.println(authentication.getName());
        response.getWriter().flush();

	}
	
	
}
