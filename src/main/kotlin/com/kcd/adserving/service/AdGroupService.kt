package com.kcd.adserving.service

import com.kcd.adserving.domain.AdGroup
import com.kcd.adserving.dto.AdGroupDto
import com.kcd.adserving.mapper.AdGroupMapper
import com.kcd.adserving.repository.AdGroupRepository
import com.kcd.adserving.repository.CampaignRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class AdGroupService(
    private val mapper: AdGroupMapper,
    private val repository: AdGroupRepository,
    private val campaignRepository: CampaignRepository,
) {

    @Transactional(readOnly = true)
    fun getAll(): MutableList<AdGroup> = repository.findAll()

    @Transactional(readOnly = true)
    fun getById(id: Long): AdGroup = repository.findById(id).orElseThrow { IllegalArgumentException("ad group not found") }

    fun create(request: AdGroupDto.Request) = mapper.toEntity(request)
        .apply {
            campaignRepository.findById(request.campaignId).orElseThrow { IllegalArgumentException("campaign not found") }.addAdGroup(this)
            this.creatives?.forEach { this.addCreative(it) }
        }
        .let { repository.save(it) }

    fun update(id: Long, request: AdGroupDto.Request) = getById(id)
        .apply {
            mapper.updateFromDto(request, this)
            campaignRepository.findById(request.campaignId).orElseThrow { IllegalArgumentException("campaign not found") }.addAdGroup(this)
            this.creatives?.forEach { this.addCreative(it) }
        }

    fun deleteById(id: Long) = repository.deleteById(id)
}
