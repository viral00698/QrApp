package com.QrApplication.Entity;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
//@Data
public class Users {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	private String email;
	
	private String password;
	
	private String roles;
	
	@OneToMany(mappedBy = "roleId", cascade = CascadeType.ALL)
	@JsonManagedReference
	private Set<Roles> role = new HashSet<>();
	
	public Users addRole(Roles role) {
		this.role.add(role);
		role.setUsers(this);
		return this;
	}
	
}
