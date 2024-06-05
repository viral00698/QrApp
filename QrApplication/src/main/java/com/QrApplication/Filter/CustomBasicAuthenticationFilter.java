package com.QrApplication.Filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomBasicAuthenticationFilter  extends OncePerRequestFilter{

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
			
			String header = request.getHeader("Authorization");
			System.err.println(header);
			
			 if (header == null || !header.startsWith("Basic ")) {
				 	filterChain.doFilter(request, response); // If the Authorization header is missing or not Basic, continue with the filter chain.
	                return;
	         }

	         try {
	                Authentication authentication = extractAuthentication(request);
	                SecurityContextHolder.getContext().setAuthentication(authentication);
	                filterChain.doFilter(request, response);
	         } catch (Exception e) {
//	                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	        	 e.printStackTrace();
	        	 System.err.println("username invalid++++++++++++++");
	          }
	}
	
	private Authentication extractAuthentication(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        byte[] base64Token = header.substring(6).getBytes(StandardCharsets.UTF_8);
        byte[] decoded;
        try {
            decoded = Base64.getDecoder().decode(base64Token);
        } catch (IllegalArgumentException e) {
            throw new BadCredentialsException("Failed to decode basic authentication token");
        }
        String token = new String(decoded, StandardCharsets.UTF_8);
        int delim = token.indexOf(":");
        if (delim == -1) {
            throw new BadCredentialsException("Invalid basic authentication token");
        }
        String username = token.substring(0, delim);
        String password = token.substring(delim + 1);
        return new UsernamePasswordAuthenticationToken(username, password);
    }
	
	

	
}
