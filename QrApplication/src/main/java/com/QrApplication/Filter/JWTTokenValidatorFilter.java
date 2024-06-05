package com.QrApplication.Filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.QrApplication.SecurityConstant.SecurityConstent;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTTokenValidatorFilter extends OncePerRequestFilter{

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
			String jwt = request.getHeader(SecurityConstent.JWT_HEADER);

			
			if(jwt!=null) {
				try {
							
					SecretKey key = Keys.hmacShaKeyFor((SecurityConstent.JWT_KEY).getBytes(StandardCharsets.UTF_8));
					Claims claims = Jwts.parserBuilder()
							.setSigningKey(key)
							.build()
							.parseClaimsJws(jwt)
							.getBody();
					
					 String username = String.valueOf(claims.get("username"));
					 String authorities = (String) claims.get("authorities");
					 
					 Authentication auth = new UsernamePasswordAuthenticationToken(username, null,
		                        AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
		                SecurityContextHolder.getContext().setAuthentication(auth);
	                       			
				  }catch (Exception e) {
					  e.printStackTrace();  
					  System.err.println("JWT Token is Invalid");
					  
				}
				
			}
			filterChain.doFilter(request, response);
	}
	
	
	@Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getServletPath().equals("login")
        	 ||request.getServletPath().equals("signup") 
        	 ||request.getServletPath().equals("custom-login");
        	
    }
	
}
