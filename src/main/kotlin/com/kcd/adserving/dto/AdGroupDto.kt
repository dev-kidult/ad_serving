package com.kcd.adserving.dto

import com.kcd.adserving.domain.Creative
import com.kcd.adserving.enums.Comparison
import com.kcd.adserving.enums.TargetInclude
import java.math.BigDecimal
import java.time.OffsetDateTime

interface AdGroupDto {

    data class Request(
        val enabled: Boolean,
        val startTime: OffsetDateTime,
        val endTime: OffsetDateTime,
        val priority: Double,
        val campaignId: Long,
        var classificationTargetInclude: TargetInclude? = null,
        var classificationTargets: MutableList<String>? = null,
        var locationSidoTargetInclude: TargetInclude? = null,
        var locationSidoTargets: MutableList<String>? = null,
        var monthlySalesComparison: Comparison? = null,
        var monthlySalesTargets: BigDecimal? = null,
        val creatives: MutableList<Creative>,
    )
}
