package com.zonix.dndapp.mapper;

import com.zonix.dndapp.dto.request.TemplateCreatureCreationRequest;
import com.zonix.dndapp.entity.TemplateCreature;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TemplateCreatureMapper {
    TemplateCreature toEntity(TemplateCreatureCreationRequest request);
}
