package com.kcd.adserving.controller

import com.kcd.adserving.dto.AdGroupDto
import com.kcd.adserving.service.AdGroupService
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
@RequestMapping("/ad-groups")
class AdGroupController(private val service: AdGroupService) {

    @GetMapping
    fun getAllUsers() = service.getAll()

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long) = service.getById(id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(request: AdGroupDto.Request) = service.create(request)

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, request: AdGroupDto.Request) = service.update(id, request)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Long) = service.deleteById(id)
}
