package com.kcd.adserving.controller

import com.kcd.adserving.dto.UserDto
import com.kcd.adserving.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {

    @GetMapping
    fun getAllUsers() = userService.getAll()

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long) = userService.getById(id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(request: UserDto.Request) = userService.create(request)

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, request: UserDto.Request) = userService.update(id, request)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Long) = userService.deleteById(id)
}
