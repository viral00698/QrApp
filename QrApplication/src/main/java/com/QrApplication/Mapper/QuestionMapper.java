package com.QrApplication.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.QrApplication.Dtos.QuestionDto;
import com.QrApplication.Entity.Question;

@Mapper(componentModel = "spring") // Enables Spring @Autowired support
public interface QuestionMapper {


    @Mapping(source = "vendor.vendorId", target = "vendorId")
    QuestionDto toDto(Question question);

    @Mapping(source = "vendorId", target = "vendor.vendorId")
    Question toEntity(QuestionDto dto);
}
