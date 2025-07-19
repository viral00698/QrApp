package com.QrApplication.AuthDto;

import java.util.List;
import com.QrApplication.Enum.UserType;
import lombok.Data;

@Data
public class UsersDto {
	
	private String email;
	private String password;
	private String name;
	private String vid;
	private String uid;
	private List<UserType> role;
	
}
