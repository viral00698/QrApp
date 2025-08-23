package com.QrApplication.Mapper;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import com.QrApplication.Dtos.EmployeeDto;
import com.QrApplication.Entity.Employee;

import java.util.List;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_DEFAULT,
    nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT
)
public interface EmployeeMapper {

    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    // Entity to DTO
    @Mapping(source = "vendor.vendorId", target = "vendorId")
    @Mapping(target = "address", ignore = true)
    EmployeeDto toDto(Employee employee);

    // DTO to Entity (vendor must be set manually after mapping)
    @Mappings({
        @Mapping(target = "vendor", ignore = true), // Set manually later
        @Mapping(target = "empId", source = "empId"),
        @Mapping(target = "name", source = "name"),
        @Mapping(target = "mobileNo", source = "mobileNo"),
        @Mapping(target = "aadharNo", source = "aadharNo"),
        @Mapping(target = "aadharDoc", source = "aadharDoc"),
        @Mapping(target = "panNo", source = "panNo"),
        @Mapping(target = "panDoc", source = "panDoc"),
        @Mapping(target = "upi", source = "upi"),
        @Mapping(target = "address", ignore = true),
        @Mapping(target = "createAt", ignore = true),
	    @Mapping(target = "users", ignore = true)
    })
    
    @Mapping(target = "addre", ignore = true)
    Employee toEntity(EmployeeDto dto);

    // Optional list mapping with null-safe config
    List<EmployeeDto> toDtoList(List<Employee> employees);
    List<Employee> toEntityList(List<EmployeeDto> dtos);
    
    // ✅ NEW METHOD for partial update (copy non-null fields only)
    @Mappings({
    	   	@Mapping(target = "address", ignore = true),
    	    @Mapping(target = "createAt", ignore = true),
    	    @Mapping(target = "vendor", ignore = true),
    	    @Mapping(target = "users", ignore = true)
    })
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "addre", ignore = true)
    void updateEmployeeFromDto(EmployeeDto dto, @MappingTarget Employee employee);
}
