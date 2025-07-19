package com.QrApplication.Mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import com.QrApplication.Dtos.VendorDto;
import com.QrApplication.Entity.Vendor;

@Mapper(componentModel = "spring" , nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface VendorMapper {
	
	  VendorMapper INSTANCE = Mappers.getMapper(VendorMapper.class);

	  	@Mapping(target = "address", ignore = true)
	    VendorDto toDto(Vendor vendor);

	    @Mapping(target = "product", ignore = true)
	    @Mapping(target = "address", ignore = true)
	    Vendor toEntity(VendorDto dto);

	    // For partial update:
	    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	    @Mapping(target = "product", ignore = true)
	    @Mapping(target = "address", ignore = true)
	    void updateVendorFromDto(VendorDto dto, @MappingTarget Vendor vendor);

}
