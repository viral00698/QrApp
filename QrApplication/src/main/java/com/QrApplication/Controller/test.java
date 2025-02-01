package com.QrApplication.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class test {
	
	@GetMapping("pg")
	public String test() {
		return "Hello I am Working";
	}
}
