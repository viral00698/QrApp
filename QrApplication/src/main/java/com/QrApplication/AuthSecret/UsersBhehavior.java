package com.QrApplication.AuthSecret;

import com.QrApplication.AuthDto.UsersDto;

public interface UsersBhehavior {
	
//	private String forgetPassword(UsersDto usersDto) {
//		return null;
//	}
//	private Boolean idActive(String email) {
//		return true;
//	}
	
	public ResponseType<String> createUser(UsersDto usersDto);
	
	
}
