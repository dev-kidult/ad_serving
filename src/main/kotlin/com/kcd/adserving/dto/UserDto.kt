package com.kcd.adserving.dto

import com.kcd.adserving.enums.Gender
import java.math.BigDecimal

interface UserDto {

    data class Request(
        val gender: Gender,
        val age: Int,
        val name: String,
        val classificationName: String,
        val locationSidoName: String,
        val monthlySales: BigDecimal,
    )
}
