package com.QrApplication.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.QrApplication.Dtos.UserDocumentsDto;
import com.QrApplication.Entity.UserDocuments;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserDocumentsMapper {

    // DTO → Entity
	
    @Mapping(target = "docId", expression = "java(dto.getDocId() != null ? UUID.fromString(dto.getDocId()) : null)")
    UserDocuments toEntity(UserDocumentsDto dto);

    // Entity → DTO
    @Mapping(target = "docId", expression = "java(entity.getDocId() != null ? entity.getDocId().toString() : null)")
    UserDocumentsDto toDto(UserDocuments entity);
}
