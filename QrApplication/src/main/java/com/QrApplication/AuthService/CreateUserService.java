package com.QrApplication.AuthService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.QrApplication.AuthDto.UsersDto;
import com.QrApplication.AuthRepository.RolesRepository;
import com.QrApplication.AuthRepository.UserRepos;
import com.QrApplication.AuthSecret.ResponseType;
import com.QrApplication.AuthSecret.UsersBhehavior;
import com.QrApplication.Entity.Roles;
import com.QrApplication.Entity.Users;
import com.QrApplication.Enum.RequestStatus;
import com.QrApplication.Enum.UserType;

@Service
public class CreateUserService implements UsersBhehavior {

	@Autowired
	private UserRepos userRepos;

	@Autowired
	private RolesRepository rolesRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public ResponseType<String> createUser(UsersDto usersDto) {

		if (usersDto == null) {
			return new ResponseType<String>().ResponseGenerator(HttpStatus.BAD_REQUEST, RequestStatus.failure,
					"Invalid Request");
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
					return new ResponseType<String>().ResponseGenerator(HttpStatus.CREATED, RequestStatus.success,
							"User Created");
				}
			} else {
				return new ResponseType<String>().ResponseGenerator(HttpStatus.BAD_REQUEST, RequestStatus.failure,
						"Username already exists");
			}

		}

		return new ResponseType<String>().ResponseGenerator(HttpStatus.BAD_REQUEST, RequestStatus.failure,
				"Invalid Request");

	}
}
