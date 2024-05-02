package com.kcd.adserving.service

import com.kcd.adserving.domain.User
import com.kcd.adserving.dto.UserDto
import com.kcd.adserving.mapper.UserMapper
import com.kcd.adserving.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserService(private val mapper: UserMapper, private val repository: UserRepository) {

    @Transactional(readOnly = true)
    fun getAll(): MutableList<User> = repository.findAll()

    @Transactional(readOnly = true)
    fun getById(id: Long): User = repository.findById(id).orElseThrow { IllegalArgumentException("user not found") }

    fun create(request: UserDto.Request): User = mapper.toEntity(request).let { repository.save(it) }

    fun update(id: Long, request: UserDto.Request) = getById(id).apply { mapper.updateFromDto(request, this) }

    fun deleteById(id: Long) = repository.deleteById(id)
}
