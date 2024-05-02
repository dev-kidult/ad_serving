package com.kcd.adserving.service

import com.kcd.adserving.domain.AdGroup
import com.kcd.adserving.domain.Campaign
import com.kcd.adserving.domain.QAdGroup.adGroup
import com.kcd.adserving.domain.QCampaign.campaign
import com.kcd.adserving.domain.QCreative.creative
import com.kcd.adserving.domain.User
import com.kcd.adserving.dto.ServingDto
import com.kcd.adserving.enums.Comparison
import com.kcd.adserving.enums.TargetInclude
import com.kcd.adserving.mapper.CreativeMapper
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.OffsetDateTime

@Service
@Transactional(readOnly = true)
class ServingService(
    private val queryFactory: JPAQueryFactory,
    private val userService: UserService,
    private val mapper: CreativeMapper,
) {

    fun recommend(id: Long, placements: List<String>): List<ServingDto.Placement> {
        val now = OffsetDateTime.now()
        val user = userService.getById(id)
        val campaign = queryFactory.select(campaign)
            .from(campaign)
            .leftJoin(campaign.adGroups, adGroup).fetchJoin()
            .leftJoin(adGroup.creatives, creative).fetchJoin()
            .where(campaign.placement.`in`(placements), adGroup.enabled.isTrue, adGroup.startTime.before(now), adGroup.endTime.after(now))
            .orderBy(adGroup.priority.asc())
            .fetch()

        return filter(user, campaign)
            .groupBy { it.placement }
            .map { (placement, campaigns) ->
                val creatives = campaigns.flatMap { it.adGroups!! }.flatMap { it.creatives!! }.map { mapper.toDto(it) }
                ServingDto.Placement(name = placement, creatives = creatives)
            }
    }

    private fun filter(user: User, campaigns: List<Campaign>) = campaigns.filter { campaign ->
        campaign.adGroups!!.all { adGroup ->
            filteringClassification(user, adGroup) && filteringLocationSido(user, adGroup) && filteringMonthlySales(user, adGroup)
        }
    }

    private fun filteringClassification(user: User, adGroup: AdGroup): Boolean {
        if (adGroup.classificationTargets != null) {
            return when (adGroup.classificationTargetInclude) {
                TargetInclude.INCLUDE -> adGroup.classificationTargets!!.contains(user.classificationName)
                TargetInclude.EXCLUDE -> !adGroup.classificationTargets!!.contains(user.classificationName)
                null -> true
            }
        }
        return true
    }

    private fun filteringLocationSido(user: User, adGroup: AdGroup): Boolean {
        if (adGroup.locationSidoTargets != null) {
            return when (adGroup.locationSidoTargetInclude) {
                TargetInclude.INCLUDE -> adGroup.locationSidoTargets!!.contains(user.locationSidoName)
                TargetInclude.EXCLUDE -> !adGroup.locationSidoTargets!!.contains(user.locationSidoName)
                null -> true
            }
        }
        return true
    }

    private fun filteringMonthlySales(user: User, adGroup: AdGroup): Boolean {
        if (adGroup.monthlySalesTargets != null) {
            return when (adGroup.monthlySalesComparison) {
                Comparison.GT -> user.monthlySales > adGroup.monthlySalesTargets
                Comparison.GOE -> user.monthlySales >= adGroup.monthlySalesTargets
                Comparison.LT -> user.monthlySales < adGroup.monthlySalesTargets
                Comparison.LOE -> user.monthlySales <= adGroup.monthlySalesTargets
                Comparison.EQ -> user.monthlySales == adGroup.monthlySalesTargets
                null -> true
            }
        }
        return true
    }
}
