package com.QrApplication.AuthController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.QrApplication.AuthDto.UsersDto;
import com.QrApplication.AuthSecret.ResponseType;
import com.QrApplication.AuthSecret.UsersBhehavior;
import com.QrApplication.Enum.RequestStatus;

@RestController
public class AuthController {
	
	@Autowired
	private UsersBhehavior usersBhehavior;

	@PostMapping("signup")
	public ResponseType createUser(@RequestBody UsersDto usersDto) {
		return this.usersBhehavior.createUser(usersDto);
	}
	
	@GetMapping("login")
	public ResponseType login() {
		return ResponseType.ResponseGenerator(RequestStatus.success, "Login successfully" , HttpStatus.ACCEPTED);
	}
	
	@GetMapping("logout")
	public ResponseType logout(){
		return ResponseType.ResponseGenerator(RequestStatus.success, "Logout successfully" ,HttpStatus.ACCEPTED);
	}
}
