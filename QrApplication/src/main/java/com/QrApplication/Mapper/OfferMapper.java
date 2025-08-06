package com.QrApplication.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import org.mapstruct.*;

import java.util.*;
import java.util.stream.Collectors;

import com.QrApplication.Dtos.OfferDto;
import com.QrApplication.Entity.Offer;
import com.QrApplication.Entity.Product;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_DEFAULT, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface OfferMapper {

	OfferMapper INSTANCE = Mappers.getMapper(OfferMapper.class);

	// Entity to DTO
	@Mapping(target = "productIds", expression = "java(mapProductIds(offer.getProducts()))")
	OfferDto toDto(Offer offer);

	// DTO to Entity (products must be set manually later)
	@Mappings({ @Mapping(target = "products", ignore = true), @Mapping(target = "createAt", ignore = true) })
	Offer toEntity(OfferDto dto);

	List<OfferDto> toDtoList(List<Offer> offers);

	List<Offer> toEntityList(List<OfferDto> offerDtos);

	// For PATCH/UPDATE: Only update non-null fields
	@Mappings({ @Mapping(target = "products", ignore = true), @Mapping(target = "createAt", ignore = true) })
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateOfferFromDto(OfferDto dto, @MappingTarget Offer offer);

	// Helper method to extract product IDs from product Set
	default Set<UUID> mapProductIds(Set<Product> products) {
		if (products == null)
			return Collections.emptySet();
		return products.stream().map(Product::getProductId).collect(Collectors.toSet());
	}
}