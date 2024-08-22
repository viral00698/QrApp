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

@RestController
public class AuthController {
	
	@Autowired
	private UsersBhehavior usersBhehavior;

	@PostMapping("signup")
	public ResponseType<String> createUser(@RequestBody UsersDto usersDto) {
		return this.usersBhehavior.createUser(usersDto);
	}
	
	@GetMapping("login")
	public ResponseType<String> login() {
		return new ResponseType<String>().ResponseGenerator(HttpStatus.OK, "Login successfully");
	}
}
