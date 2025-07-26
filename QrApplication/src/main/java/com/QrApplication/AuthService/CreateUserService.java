package com.QrApplication.AuthService;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.QrApplication.AuthDto.UsersDto;
import com.QrApplication.AuthRepository.RolesRepository;
import com.QrApplication.AuthRepository.UserRepos;
import com.QrApplication.AuthSecret.ResponseType;
import com.QrApplication.AuthSecret.UsersBhehavior;
import com.QrApplication.Entity.Employee;
import com.QrApplication.Entity.Roles;
import com.QrApplication.Entity.Users;
import com.QrApplication.Entity.Vendor;
import com.QrApplication.Enum.RequestStatus;
import com.QrApplication.Enum.UserType;
import com.QrApplication.Repository.EmployeeRepos;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class CreateUserService implements UsersBhehavior {

	@Autowired
	private UserRepos userRepos;

	@Autowired
	private RolesRepository rolesRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private EmployeeRepos employeeRepos;

	public ResponseType createUser(UsersDto usersDto) {

		if (usersDto == null) {
			return ResponseType.ResponseGenerator(RequestStatus.failure,"Invalid Request",HttpStatus.BAD_REQUEST);
		}

		if (usersDto.getEmail() != null || usersDto.getPassword() != null || usersDto.getRole() != null || usersDto.getName()!=null) {
			Boolean b = this.userRepos.existsByEmail(usersDto.getEmail());
			System.err.println(b);
			if (!b) {
				Users users = new Users();
				users.setEmail(usersDto.getEmail());
				String pwd = passwordEncoder.encode(usersDto.getPassword());
				users.setPassword(pwd);
				users.setCreateDate(new Date());
				users.setName(usersDto.getName());
				
				
				if(usersDto.getVid()!=null) {
					Vendor vendor = new Vendor();
					vendor.setVendorId(UUID.fromString(usersDto.getVid()));
					users.setVendorDetails(vendor);
				}
				
				if(!usersDto.getUid().equals("NULL")) {
					Employee employee = employeeRepos.findById(UUID.fromString(usersDto.getUid())).get();
					if(employee!=null) {
						users.setEmployee(employee);
					}
					
				}
				
			
				this.userRepos.save(users);

				List<Users> u = this.userRepos.findByEmail(usersDto.getEmail());

				if (!u.isEmpty()) {

					List<Roles> tmp = new ArrayList<>();

					for (UserType s : usersDto.getRole()) {
						Roles r = new Roles();
						r.setUserType(s);
						r.setUsers(u.get(0));
						tmp.add(r);
					}

					this.rolesRepository.saveAll(tmp);
					return ResponseType.ResponseGenerator(RequestStatus.success,
							"User Created",HttpStatus.CREATED);
				}
			} else {
				return ResponseType.ResponseGenerator(RequestStatus.failure,
						"Username already exists" , HttpStatus.BAD_REQUEST);
			}

		}

		return ResponseType.ResponseGenerator(RequestStatus.failure,
				"Invalid Request" , HttpStatus.BAD_REQUEST);

	}

	public ResponseType getUsername(HttpServletRequest request) {
		  String header = request.getHeader("Authorization");
	        byte[] base64Token = header.substring(6).getBytes(StandardCharsets.UTF_8);
	        byte[] decoded;
	        try {
	            decoded = Base64.getDecoder().decode(base64Token);
	        } catch (IllegalArgumentException e) {
	           return null;
	        }
	        String token = new String(decoded, StandardCharsets.UTF_8);
	        int delim = token.indexOf(":");
	        if (delim == -1) {
	           return null;
	        }
	        String username = token.substring(0, delim);
	        if(!username.equals(null))
	        	return ResponseType.ResponseGenerator(RequestStatus.success, "Login successfully" , getUserDetails(username));
	        else
	        	return ResponseType.ResponseGenerator(RequestStatus.success, "Login un-successfully");
	}

	@Override
	public Users getUserDetails(String username) {
		try {
			return userRepos.getUserByEmail(username);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public ResponseType forgetPassword(UsersDto usersDto) {
		
		try {
			if(usersDto==null || usersDto.getEmail() == null || usersDto.getPassword() == null) {
				return ResponseType.ResponseGenerator(RequestStatus.failure, "Request invalid");
			}
			
		    Users users = userRepos.findByEmail(usersDto.getEmail()).get(0);
		    if (users != null) {
		        String pwd = passwordEncoder.encode(usersDto.getPassword());
		        users.setPassword(pwd);
		        userRepos.save(users);
		        return ResponseType.ResponseGenerator(RequestStatus.success, "Password changed successfully");
		    }
		    return ResponseType.ResponseGenerator(RequestStatus.failure, "User not found");

		} catch (Exception e) {
			e.printStackTrace();
			  return ResponseType.ResponseGenerator(RequestStatus.failure, "getting error from server side");
		}
		
	
	  
	    
	}
	
}
