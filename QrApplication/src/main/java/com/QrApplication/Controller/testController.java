package com.QrApplication.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.QrApplication.AuthRepository.UserRepos;
import com.QrApplication.Entity.Roles;
import com.QrApplication.Entity.Users;
import com.QrApplication.Enum.UserType;


@RestController
public class testController {
	
	@Autowired
	private UserRepos userRepos;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("test")
	public String test() {
		return "ok";
	}
	@GetMapping("home")
	public ResponseEntity<?> home() {
		return null;
	}
	
	@GetMapping("signup")
	public String signup() {
		
		Users users = new Users();
		users.setEmail("test4@gmail.com");
		users.setPassword(passwordEncoder.encode("123"));
		users.setRoles("user");
		
		Roles r1 = new Roles();
		r1.setUserType(UserType.User);
//		r2.setUserType(UserType.Admin);
		Users e =  users.addRole(r1);
		System.err.println(e.getId());
//		users.addRole(r2);
	
		userRepos.save(users);
		
		return "ok";
	}
	
	@GetMapping("/setRole")
	public String getUserDetailsAfterLogin() {
		return "ok";
	}
	
	
	@GetMapping("login")
	public String Login(Authentication authentication) {
	      return "ok";
	}
	
	@GetMapping("testSecure")
	public String testSecure() {
	      return "hello testSecure";
	}
	@GetMapping("testSecure2")
	public String testSecure2() {
	      return "hello testSecure2";
	}
	
	
}
