package com.QrApplication.Mapper;

import java.util.UUID;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.QrApplication.Dtos.AddressDto;
import com.QrApplication.Entity.Address;
import com.QrApplication.Entity.Users;
import com.QrApplication.Entity.Vendor;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AddressMapper {

	@Mapping(source = "userId", target = "users", qualifiedByName = "mapUser")
	@Mapping(source = "vendorId", target = "vendor", qualifiedByName = "mapVendor")
	@Mapping(target = "employee", ignore = true)
	Address toEntity(AddressDto dto);

	@Mapping(source = "users.id", target = "userId")
	@Mapping(source = "vendor.vendorId", target = "vendorId")
	AddressDto toDto(Address address);
	
	 @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	 @Mapping(target = "employee", ignore = true)
	 @Mapping(target = "users", ignore = true)
	 @Mapping(target = "vendor", ignore = true)
	 void updateAddressFromDto(AddressDto dto, @MappingTarget Address address);

	@Named("mapVendor")
	static Vendor mapVendor(UUID vendorId) {
		if (vendorId == null)
			return null;
		Vendor vendor = new Vendor();
		vendor.setVendorId(vendorId);
		return vendor;
	}

	@Named("mapUser")
	static Users mapUser(UUID userId) {
		if (userId == null)
			return null;
		Users user = new Users();
		user.setId(userId);
		return user;
	}
}
