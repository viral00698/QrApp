package com.QrApplication.Filter;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.QrApplication.SecurityConstant.SecurityConstent;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtSocketHandshakeInterceptor implements HandshakeInterceptor{

	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {
	
		List<String> authHeaders = request.getHeaders().get(SecurityConstent.JWT_HEADER);
		System.err.println("++++++++++++++++++++++++++++++++++++++++++++++++++Helllo+++++++++++++++++++++++++++++++");
		System.err.println(request.getHeaders());
		System.err.println("++++++++++++++++++++++++++++++++++++++++++++++++++Helllo+++++++++++++++++++++++++++++++");
//		if( authHeaders !=null && jwt!=null) {
//			try {
//						
//				SecretKey key = Keys.hmacShaKeyFor((SecurityConstent.JWT_KEY).getBytes(StandardCharsets.UTF_8));
//				Claims claims = Jwts.parserBuilder()
//						.setSigningKey(key)
//						.build()
//						.parseClaimsJws(jwt)
//						.getBody();
//				
//				 String username = String.valueOf(claims.get("username"));
//				 String authorities = (String) claims.get("authorities");
//				 	;
//				 Authentication auth = new UsernamePasswordAuthenticationToken(username, null,
//	                AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
//	                SecurityContextHolder.getContext().setAuthentication(auth);
//
//			  }catch (Exception e) {
//				  e.printStackTrace();  
//				  System.err.println("JWT Token is Invalid socket");
//			}
			
//		}
		
		return true;
	}

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception exception) {
		// TODO Auto-generated method stub
		
	}
	
	

}
