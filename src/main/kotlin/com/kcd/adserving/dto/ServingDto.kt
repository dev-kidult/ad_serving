package com.kcd.adserving.dto

interface ServingDto {

    data class Response(
        val placements: List<Placement>,
    )

    data class Placement(
        val name: String,
        val creatives: List<Creative>,
    )

    data class Creative(
        val adGroupId: Long,
        val creativeId: Long,
        val title: String,
        val description: String,
        val textColor: String,
        val backgroundColor: String,
        val backgroundImage: String,
        val url: String,
        val priority: Double,
    )
}
