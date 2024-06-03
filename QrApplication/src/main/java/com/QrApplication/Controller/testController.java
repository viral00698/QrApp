package com.QrApplication.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.QrApplication.Entity.Users;
import com.QrApplication.Services.UserRepos;


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
		users.setEmail("abc@gmail.com");
		
		users.setPassword(passwordEncoder.encode("123"));
		users.setRoles("user");
		
		userRepos.save(users);
		
		return "ok";
	}
	
	@GetMapping("/user")
	public String getUserDetailsAfterLogin(Authentication authentication) {
//	        List<Users> customers = userRepos.findByEmail(authentication.getName());
//	        if (customers.size() > 0) {
//	            return customers.get(0);
//	        } else {
//	            return null;
//	        }
//	       
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
	
	
}
