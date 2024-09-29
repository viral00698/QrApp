package com.QrApplication.Entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;

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
	
	@Column(unique = true ,nullable = false)
	private String email;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;
		
	@Column(nullable = false)
	private String name;
	
	private Date createDate;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonManagedReference
	private Vendor vendorDetails;
	
	@OneToMany(mappedBy = "users", fetch = FetchType.EAGER)
	@JsonManagedReference
	private Set<Roles> role = new HashSet<>();
	
	@OneToMany(mappedBy = "users", fetch = FetchType.EAGER)
	@JsonManagedReference
	private Set<Address> address= new HashSet<>();
	
//	@OneToMany(mappedBy = "users", fetch = FetchType.EAGER)
//	@JsonManagedReference
//	private Set<Product> product= new HashSet<>();
	
	

	
}
