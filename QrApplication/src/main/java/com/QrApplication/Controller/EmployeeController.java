package com.QrApplication.Controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.QrApplication.AuthSecret.ResponseType;
import com.QrApplication.Dtos.EmployeeDto;
import com.QrApplication.Service.EmployeeService;

@RestController
@RequestMapping("employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	@PostMapping("create")
	ResponseType createEmployee(@RequestBody EmployeeDto employeeDto) {
		return employeeService.createEmployee(employeeDto);
	}
	
	@PostMapping("employees")
	ResponseType getEmployeeByVendor(@RequestBody EmployeeDto employeeDto) {
		return employeeService.getEmployeeByVendor(UUID.fromString(employeeDto.getVendorId()));
	}
	
	@PostMapping("changeEmployeeStatus")
	ResponseType changeEmployeeStatus(@RequestBody EmployeeDto employeeDto) {
		return employeeService.changeEmployeeStatus(employeeDto);
	}
	
	@GetMapping("getAddress/{empid}")
	ResponseType getEmployeeAddress(@PathVariable String empid) {
		return employeeService.getEmployeeAddress(UUID.fromString(empid));
	}
}
