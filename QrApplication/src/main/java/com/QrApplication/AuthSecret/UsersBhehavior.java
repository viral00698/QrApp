package com.QrApplication.AuthSecret;

import com.QrApplication.AuthDto.UsersDto;
import com.QrApplication.Entity.Users;

import jakarta.servlet.http.HttpServletRequest;

public interface UsersBhehavior {
	
//	private String forgetPassword(UsersDto usersDto) {
//		return null;
//	}
//	private Boolean idActive(String email) {
//		return true;
//	}
	
	public ResponseType createUser(UsersDto usersDto);
	public ResponseType getUsername(HttpServletRequest request);
	public Users getUserDetails(String username);
	
}
