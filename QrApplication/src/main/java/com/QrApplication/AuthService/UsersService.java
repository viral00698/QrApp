package com.QrApplication.AuthService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.QrApplication.AuthRepository.UserRepos;
import com.QrApplication.Entity.Roles;
import com.QrApplication.Entity.Users;


@Component
public class UsersService implements AuthenticationProvider {
	
	@Autowired
	private UserRepos userRepos;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();
		
		
		List<Users> user = this.userRepos.findByEmail(username);
		
		if(!user.isEmpty()) {
			if(passwordEncoder.matches(password, user.get(0).getPassword())){
//				List<GrantedAuthority> authorities = new ArrayList<>();
//				authorities.add(new SimpleGrantedAuthority("user"));
				return new UsernamePasswordAuthenticationToken(username,password,getRolesFromUserSet(user.get(0).getRole()));
			}else {
				 System.err.println("Username Invalid");
				 authentication.setAuthenticated(false);
				 throw new BadCredentialsException("Username Invalid");
			}
		}else {
			throw new BadCredentialsException("Username Invalid");
		}
		
	}
	

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}
	
	
	private List<GrantedAuthority> getRolesFromUserSet(Set<Roles> role) {
		List<GrantedAuthority> authorities = new ArrayList<>();
			
			for(Roles r : role) {
				authorities.add(new SimpleGrantedAuthority(r.getUserType().toString()));
			}
			
			return authorities;
	}

}
