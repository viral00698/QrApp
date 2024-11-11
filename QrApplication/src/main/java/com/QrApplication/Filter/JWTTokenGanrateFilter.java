package com.QrApplication.Filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.filter.OncePerRequestFilter;

import com.QrApplication.SecurityConstant.SecurityConstent;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTTokenGanrateFilter extends OncePerRequestFilter{

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
//		System.err.println( request.getServletPath());
//		if( authentication==null) {
//			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//			response.setHeader("X-XSRF-TOKEN", "");
//			filterChain.doFilter(request, response);
//		}
		
	  try {
		
			if(authentication!=null && !authentication.getName().equals("anonymousUser") && request.getServletPath().equals("/login")) {
				SecretKey key = Keys.hmacShaKeyFor((SecurityConstent.JWT_KEY).getBytes(StandardCharsets.UTF_8));
				String jwt = Jwts.builder()
							.claim("username", authentication.getName())
							.claim("authorities", populateAuthorities(authentication.getAuthorities()))
							.setSubject("JWT Token")
							.setIssuer(SecurityConstent.TOKEN_ISSUER)
							.setIssuedAt(new Date())
							.setExpiration(new Date( new Date().getTime() + 9000000))
							.signWith(key).compact();
							response.setHeader(SecurityConstent.JWT_HEADER, jwt);
			}
		}catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		filterChain.doFilter(request, response);
		
	}
	
	 private String populateAuthorities(Collection<? extends GrantedAuthority> collection) {
	        Set<String> authoritiesSet = new HashSet<>();
	        for (GrantedAuthority authority : collection) {
	            authoritiesSet.add("ROLE_"+authority.getAuthority());
	        }
	        return String.join(",", authoritiesSet);
	 }
	 
	 
//	 @Override
//	    protected boolean shouldNotFilter(HttpServletRequest request) {
//	        return !request.getServletPath().equals("//");
//	    }


	
}


