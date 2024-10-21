package com.QrApplication.Entity;

import java.util.UUID;

import com.QrApplication.Enum.UserType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Roles {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID roleId;
	
	@Enumerated(EnumType.STRING)
	@Column(unique = false)
	private UserType userType;
	
	@ManyToOne
	@JoinColumn(name = "id")
//	@JsonBackReference(value = "role-user")
	private Users users;
}
