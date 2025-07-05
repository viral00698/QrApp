package com.QrApplication.Mapper;


import java.util.UUID;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import com.QrApplication.Dtos.ProductDto;
import com.QrApplication.Entity.Offer;
import com.QrApplication.Entity.Product;
import com.QrApplication.Entity.Vendor;

@Mapper(componentModel = "spring") // Enables Spring @Autowired support
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);



    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "vendor", source = "vendorId", qualifiedByName = "mapVendor")
    @Mapping(target = "offer", source = "offerId", qualifiedByName = "mapOffer")
    void updateProductFromDto(ProductDto dto, @MappingTarget Product product);
    
    @Mapping(target = "vendor", source = "vendorId", qualifiedByName = "mapVendor")
    @Mapping(target = "offer", source = "offerId", qualifiedByName = "mapOffer")
    Product toEntity(ProductDto dto);

    @Named("mapVendor")
    static Vendor mapVendor(UUID vendorId) {
        if (vendorId == null) return null;
        Vendor vendor = new Vendor();
        vendor.setVendorId(vendorId);
        return vendor;
    }

    @Named("mapOffer")
    static Offer mapOffer(UUID offerId) {
        if (offerId == null) return null;
        Offer offer = new Offer();
        offer.setOfferId(offerId);
        return offer;
    }
    
    @Mapping(target = "offerId", source = "offer", qualifiedByName = "extractOfferId")
    @Mapping(target = "vendorId", source = "vendor", qualifiedByName = "extractVendorId")
    ProductDto toDto(Product entity);

    @Named("extractOfferId")
    default UUID extractOfferId(Offer offer) {
        return (offer != null) ? offer.getOfferId() : null;
    }

    @Named("extractVendorId")
    default UUID extractVendorId(Vendor vendor) {
        return (vendor != null) ? vendor.getVendorId() : null;
    }
}

