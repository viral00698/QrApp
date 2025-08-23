package com.QrApplication.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.QrApplication.AuthRepository.UserRepos;
import com.QrApplication.AuthSecret.ResponseType;
import com.QrApplication.Dtos.EmailEmpIdDto;
import com.QrApplication.Dtos.EmployeeDto;
import com.QrApplication.Entity.Address;
import com.QrApplication.Entity.Employee;
import com.QrApplication.Entity.Users;
import com.QrApplication.Entity.Vendor;
import com.QrApplication.Enum.RequestStatus;
import com.QrApplication.Implementation.DocumentUpload;
import com.QrApplication.Mapper.AddressMapper;
import com.QrApplication.Mapper.EmployeeMapper;
import com.QrApplication.Repository.AddressRepos;
import com.QrApplication.Repository.EmployeeRepos;

@Service
public class EmployeeService {

	@Value("${aadhaar.doc.dir:/home/ubuntu/AADHAR_DOC/}")
	private String addharDoc;

	@Value("${pan.doc.dir:/home/ubuntu/PAN_DOC/}")
	private String panDoc;

	@Value("${empPhoto.doc.dir:/home/ubuntu/EPM_IMAGE/}")
	private String empImage;

	@Autowired
	private AddressRepos addressRepos;

	@Autowired
	private EmployeeMapper employeeMapper;

	@Autowired
	private EmployeeRepos employeeRepos;

	@Autowired
	private AddressMapper addressMapper;

	@Autowired
	private DocumentUpload documentUpload;
	
	@Autowired
	private UserRepos userRepos;
	
	
	
	public ResponseType createEmployee(EmployeeDto employeeDto) {

		try {

			if (employeeDto.getEmpId() != null) {
				return updateEmployee(employeeDto);
			}

			String aadhaarResponse = documentUpload.uploadFile(addharDoc, employeeDto.getAadharDoc());
			employeeDto.setAadharDoc(aadhaarResponse);

			String panResponse = documentUpload.uploadFile(panDoc, employeeDto.getPanDoc());
			employeeDto.setPanDoc(panResponse);

			String restroResponse = documentUpload.uploadFile(empImage, employeeDto.getEmpImage());
			employeeDto.setEmpImage(restroResponse);

			Employee employee = employeeMapper.toEntity(employeeDto);
			Address address = addressMapper.toEntity(employeeDto.getAddress());

			Vendor v = new Vendor();
			v.setVendorId(UUID.fromString(employeeDto.getVendorId()));
			employee.setVendor(v);

			employee.setCreateAt(new Date());

			Employee rs = employeeRepos.save(employee);
			address.setEmployee(rs);

			Address a = addressRepos.save(address);
			Set<Address> tmp = new HashSet<>();
			tmp.add(a);
			rs.setAddress(tmp);

			return ResponseType.ResponseGenerator(RequestStatus.success, "Employee Created", rs);

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseType.ResponseGenerator(RequestStatus.failure, "Getting error while createing employee");
		}

	}

	public ResponseType updateEmployee(EmployeeDto employeeDto) {

		try {
			System.err.println(employeeDto);
			Employee employee = null;
			if (employeeDto.getEmpId() != null) {
			    Optional<Employee> employeeOpt = employeeRepos.findById(UUID.fromString(employeeDto.getEmpId()));
			    if (employeeOpt.isPresent()) {
			        employee = employeeOpt.get();
			    }else {
			    	return ResponseType.ResponseGenerator(RequestStatus.failure,
							"Employee not found with ID: " + employeeDto.getEmpId());
			    }
			}else {
				return ResponseType.ResponseGenerator(RequestStatus.failure,
						"Employee not found with ID: " + employeeDto.getEmpId());
			}
				
		
			if (employeeDto.getAadharDoc() != null && !employeeDto.getAadharDoc().equals(employee.getAadharDoc())) {
				String aadhaarResponse = documentUpload.uploadFile(addharDoc, employeeDto.getAadharDoc());
				employeeDto.setAadharDoc(aadhaarResponse);
			}

			if (employeeDto.getPanDoc() != null && !employeeDto.getPanDoc().equals(employee.getPanDoc())) {
				String panResponse = documentUpload.uploadFile(panDoc, employeeDto.getPanDoc());
				employeeDto.setPanDoc(panResponse);
			}

			if (employeeDto.getEmpImage() != null && !employeeDto.getEmpImage().equals(employee.getEmpImage())) {
				String restroResponse = documentUpload.uploadFile(empImage, employeeDto.getEmpImage());
				employeeDto.setEmpImage(restroResponse);
			}
			
			employeeMapper.updateEmployeeFromDto(employeeDto, employee);
			Employee e = employeeRepos.save(employee);
			
			
			// ✅ Handle Address logic
			Address address = null;
			if (employeeDto.getAddress() != null) {
			    String addressId = employeeDto.getAddress().getAddressId();

			    // Try to load existing address
			    if (addressId != null) {
			        Optional<Address> addressOpt = addressRepos.findById(UUID.fromString(addressId));
			        if (addressOpt.isPresent()) {
			            address = addressOpt.get();
			        }
			    }

			    // If address not found, create new
			    if (address == null) {
			        Address newAddress = addressMapper.toEntity(employeeDto.getAddress());
			        newAddress.setEmployee(employee);
			        address = addressRepos.save(newAddress);
			    } else {
			        addressMapper.updateAddressFromDto(employeeDto.getAddress(), address);
			        address = addressRepos.save(address);  
			    }
			}
			
			return ResponseType.ResponseGenerator(RequestStatus.success, "Employee updated successfully", e);

		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			return ResponseType.ResponseGenerator(RequestStatus.failure, "Employee update failed: " + e.getMessage());
		}

	}

	public ResponseType getEmployeeByVendor(UUID vendorId) {
		try {

			Vendor v = new Vendor();
			v.setVendorId(vendorId);
			List<Employee> res = employeeRepos.findByVendor(v);
			
//			List<UUID> ids = new ArrayList<>();
//			for(Employee e : res) {
//				ids.add(e.getEmpId());
//			}
//			
//			List<Address> address = addressRepos.getAddressByEmployesId(ids);
//			
//			HashMap<UUID, Address> map = new HashMap<>();
//			for(Address a : address) {
//				map.put(a.getEmployee().getEmpId(), a);
//			}
//			
//			List<Employee> response = new ArrayList<>();
//			for(Employee e : res) {
//				if(map.containsKey(e.getEmpId())) {
//					e.setAddre(map.get(e.getEmpId()));
//					response.add(e);
//				}else {
//					response.add(e);
//				}
//			}
			
			
			
			return ResponseType.ResponseGenerator(RequestStatus.success, res);
		} catch (Exception e) {
			return ResponseType.ResponseGenerator(RequestStatus.failure, "Getting error while fatch employee");
		}
	}

	public ResponseType changeEmployeeStatus(EmployeeDto employeeDto) {
		
		try {
			int res = employeeRepos.changeEmployeeStatus(employeeDto.getStatus(),
					UUID.fromString(employeeDto.getVendorId()), UUID.fromString(employeeDto.getEmpId()));
			if (res > 0) {
				return ResponseType.ResponseGenerator(RequestStatus.success, "Employee status updated");
			} else {
				return ResponseType.ResponseGenerator(RequestStatus.success, "Invalid Request");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseType.ResponseGenerator(RequestStatus.success, "getting error while change update employee");
		}
	}

	public ResponseType getEmployeeAddress(UUID empId) {

		try {
			Employee emp = new Employee();
			emp.setEmpId(empId);
			List<Address> res = addressRepos.findByEmployee(emp);
			if (!res.isEmpty())
				return ResponseType.ResponseGenerator(RequestStatus.success, res.getFirst());
			return ResponseType.ResponseGenerator(RequestStatus.success, "Invalid Request");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseType.ResponseGenerator(RequestStatus.failure, "getting error while get  employee address");
		}

	}

	public ResponseType getEmailByVid(UUID vid) {
	    try {
	        List<EmailEmpIdDto> u = userRepos.getEmailByVid(vid);
	      
	        HashMap<UUID, String> map = new HashMap<>();
	        
	        for (EmailEmpIdDto obj : u) {
	            if (obj != null && obj.employeeId() != null) {
	                map.put(obj.employeeId().getEmpId(), obj.email());
	            }
	        }
	        
	        return ResponseType.ResponseGenerator(RequestStatus.success , "Emails fetched successfully", map);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseType.ResponseGenerator(RequestStatus.failure , e.getMessage());
	       
	    }
	}

}
