package com.kcd.adserving.mapper

import com.kcd.adserving.domain.AdGroup
import com.kcd.adserving.dto.AdGroupDto
import org.mapstruct.BeanMapping
import org.mapstruct.Mapper
import org.mapstruct.MappingTarget
import org.mapstruct.NullValuePropertyMappingStrategy
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
abstract class AdGroupMapper {

    abstract fun toEntity(request: AdGroupDto.Request): AdGroup

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    abstract fun updateFromDto(request: AdGroupDto.Request, @MappingTarget adGroup: AdGroup)
}
