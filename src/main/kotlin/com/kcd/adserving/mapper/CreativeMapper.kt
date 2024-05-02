package com.kcd.adserving.mapper

import com.kcd.adserving.domain.Creative
import com.kcd.adserving.dto.ServingDto
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
abstract class CreativeMapper {

    @Mappings(
        Mapping(target = "adGroupId", source = "adGroup.id"),
        Mapping(target = "creativeId", source = "id"),
        Mapping(target = "priority", source = "adGroup.priority")
    )
    abstract fun toDto(creative: Creative): ServingDto.Creative
}
