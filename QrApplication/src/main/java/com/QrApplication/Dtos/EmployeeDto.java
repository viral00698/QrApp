package com.QrApplication.Dtos;

import java.util.UUID;

import com.QrApplication.Enum.Designation;
import com.QrApplication.Enum.EmploymentType;

import ch.qos.logback.core.subst.Token.Type;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class EmployeeDto {

    private String empId;
    private String name;
    private String mobileNo;
    private String aadharNo;
    private String aadharDoc;
    private String panNo;
    private String panDoc;
    private String upi;
    private String vendorId;
    
    @Enumerated(EnumType.STRING)
    private Designation designation;
    private AddressDto address;
    private String empImage;
    private Double salary;
    private Boolean status;
    @Enumerated(EnumType.STRING)
    private EmploymentType employmentType;

	
}
