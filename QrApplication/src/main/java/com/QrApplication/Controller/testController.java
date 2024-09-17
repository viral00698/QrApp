package com.QrApplication.Controller;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.QrApplication.AuthRepository.UserRepos;
import com.QrApplication.AuthSecret.ResponseType;
import com.QrApplication.Entity.Product;
import com.QrApplication.Entity.Roles;
import com.QrApplication.Entity.Users;
import com.QrApplication.Entity.Vendor;
import com.QrApplication.Enum.RequestStatus;
import com.QrApplication.Enum.UserType;
import com.QrApplication.Repository.ProductRepository;
import com.QrApplication.Repository.VenderRepository;


@RestController
@CrossOrigin("*")
@RequestMapping("/test")
public class testController {
	
	@Autowired
	private UserRepos userRepos;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private VenderRepository venderRepository;
	

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
//		users.setRoles("user");
		
		Roles r1 = new Roles();
		r1.setUserType(UserType.USER);
//		r2.setUserType(UserType.Admin);
//		Users e =  users.addRole(r1);
//		System.err.println(e.getId());
//		users.addRole(r2);
	
		userRepos.save(users);
		
		return "ok";
	}
	
	@GetMapping("/setRole")
	public String getUserDetailsAfterLogin() {
		return "ok";
	}
	
	
//	@GetMapping("login")
//	public ResponseType<String> Login(Authentication authentication) {
//	      return new ResponseType<String>().ResponseGenerator(HttpStatus.ACCEPTED);
//	}
	
	@GetMapping("/testSecureAdmin/viral")
	public  ResponseType testSecure() {
		return ResponseType.ResponseGenerator(RequestStatus.success , "ok");
	}
	@GetMapping("/testSecureAdmin1/viral")
	public  ResponseType testSecure1() {
		return ResponseType.ResponseGenerator(RequestStatus.success,"ok");
	}
	@GetMapping("testSecure2")
	public String testSecure2() {
	      return "hello testSecure2";
	}
	
	@GetMapping("/saveProduct")
	public ResponseType saveProduct() {
	    
//		Users users = userRepos.findById(UUID.fromString("a1e68d9a-4d59-4f25-a579-2bb23e928686")).get();
//		if(users!=null) {
//		Product product = Product.builder()
//				.itemName("Samosa")
//				.description("This is test item")
//				.quantity(2)
//				.status(true)
//				.users(users)
//				.build();
//		
//		this.productRepository.save(product);
//		
//		}
	     
	     Vendor vendor = new Vendor();
	     vendor.setFssiNo("FSSI123456789");
	     vendor.setSgstCharge(5.67);
	     vendor.setSgstCharge(3.45);
	     vendor.setOwnerName("Test Vendor");
	     vendor.setPhoto("testImage.png");
	     vendor.setResturentCharge(4.5);
	     vendor.setOwnerName("Vendor test owner");
	     vendor.setStatus(true);
	     vendor.setStoreName("TestStore");
	     vendor.setGstNo("GST123456789");
	     vendor.setGstCharge(5.54);
		vendor.setCreateAt(new Date());
		this.venderRepository.save(vendor);
		
		
		
		
		return ResponseType.ResponseGenerator(RequestStatus.success , "ok");
	}
	
	@PostMapping("add")
	void addDumyProduct(@RequestBody Product product){
	
		if(product!=null) {
			
		}
		
	}
	
	
}
