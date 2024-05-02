package com.kcd.adserving.mapper

import com.kcd.adserving.domain.User
import com.kcd.adserving.dto.UserDto
import org.mapstruct.BeanMapping
import org.mapstruct.Mapper
import org.mapstruct.MappingTarget
import org.mapstruct.NullValuePropertyMappingStrategy
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,)
abstract class UserMapper {

    abstract fun toEntity(request: UserDto.Request): User

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    abstract fun updateFromDto(request: UserDto.Request, @MappingTarget user: User)
}
