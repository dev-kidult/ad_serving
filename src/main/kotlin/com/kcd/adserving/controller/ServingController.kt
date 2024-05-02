package com.kcd.adserving.controller

import com.kcd.adserving.service.ServingService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ServingController(private val servingService: ServingService) {

    @GetMapping("/recommend")
    fun recommend(@RequestParam id: Long, @RequestParam placements: List<String>) = servingService.recommend(id, placements)
}
