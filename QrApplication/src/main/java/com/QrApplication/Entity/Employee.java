package com.QrApplication.Entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.QrApplication.Enum.Designation;
import com.QrApplication.Enum.EmploymentType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString(exclude = "users")
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID )
	private UUID empId;
	
	@Column(nullable = false)
	private String name;
	
	@Enumerated(EnumType.STRING)
	private Designation designation;
	
	@Column(nullable = false)
	private String mobileNo;
	
	@Column(nullable = false)
	private String aadharNo;
	private String aadharDoc;
	
	private String panNo;
	private String panDoc;
	
	private Double salary;
	
	private Boolean status = false;
	
	@Enumerated(EnumType.STRING)
	private EmploymentType employmentType;
	
	private String upi;
	private String empImage;
	
	@Temporal(TemporalType.DATE)
	private Date createAt;
	
	@Transient
	private Address addre;

	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id") // this creates a foreign key in the Employee table
    private Vendor vendor;
 
	@JsonIgnore
	@OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
	private  Set<Address> address = new HashSet<>();
	
	@JsonIgnore
	@OneToOne(mappedBy = "employee")
	private Users users;
	
	
	
}
