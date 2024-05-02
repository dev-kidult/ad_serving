package com.kcd.adserving.service

import com.kcd.adserving.domain.AdGroup
import com.kcd.adserving.domain.Campaign
import com.kcd.adserving.domain.Creative
import com.kcd.adserving.dto.AdGroupDto
import com.kcd.adserving.enums.TargetInclude
import com.kcd.adserving.mapper.AdGroupMapper
import com.kcd.adserving.mapper.AdGroupMapperImpl
import com.kcd.adserving.repository.AdGroupRepository
import com.kcd.adserving.repository.CampaignRepository
import com.ninjasquad.springmockk.MockkBean
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.just
import io.mockk.runs
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.OffsetDateTime
import java.util.Optional

@SpringBootTest(classes = [AdGroupService::class, AdGroupMapperImpl::class])
class AdGroupServiceTest(
    @Autowired private val service: AdGroupService,
    @Autowired private val mapper: AdGroupMapper,
    @MockkBean private val repository: AdGroupRepository,
    @MockkBean private val campaignRepository: CampaignRepository,
) : BehaviorSpec({

    val campaign = Campaign(
        id = 1L,
        placement = "placement",
        adGroups = mutableSetOf(),
    )

    val adGroup = AdGroup(
        id = 1L,
        enabled = true,
        startTime = OffsetDateTime.now(),
        endTime = OffsetDateTime.now().plusDays(30),
        priority = 1.0,
        classificationTargetInclude = TargetInclude.INCLUDE,
        classificationTargets = mutableListOf("developer"),
        campaign = null,
        creatives = mutableSetOf(),
    )

    val creative = Creative(
        id = 1L,
        title = "title",
        description = "description",
        textColor = "#000000",
        backgroundColor = "#FFFFFF",
        backgroundImage = "image_url",
        url = "url",
        adGroup = null
    )

    val reqeust = AdGroupDto.Request(
        enabled = true,
        startTime = OffsetDateTime.now(),
        endTime = OffsetDateTime.now().plusDays(30),
        priority = 1.0,
        classificationTargetInclude = TargetInclude.INCLUDE,
        classificationTargets = mutableListOf("developer"),
        campaignId = 1L,
        creatives = mutableListOf(creative),
    )

    beforeTest {
        campaign.addAdGroup(adGroup)
        adGroup.addCreative(creative)
    }

    Given("광고그룹 서비스에서") {
        When("광고그룹 전체 조회") {
            every { repository.findAll() } returns mutableListOf(adGroup)
            val result = service.getAll()
            Then("광고그룹 전체 조회 성공") {
                result.size shouldBe 1
            }
        }

        When("광고그룹 단건 조회") {
            every { repository.findById(any()) } returns Optional.of(adGroup)
            val result = service.getById(1L)
            Then("광고그룹 조회 성공") {
                result.id shouldBe 1
            }
        }

        When("광고그룹 단건 조회 실패") {
            every { repository.findById(any()) } returns Optional.empty()
            val result = shouldThrow<IllegalArgumentException> { service.getById(1L) }
            Then("광고그룹 조회 실패") {
                result.message shouldBe "ad group not found"
            }
        }

        When("광고그룹 생성") {
            every { campaignRepository.findById(any()) } returns Optional.of(campaign)
            every { repository.save(any()) } returns adGroup
            val result = service.create(reqeust)
            Then("광고그룹 생성 성공") {
                result.id shouldBe 1
            }
        }

        When("광고그룹 수정") {
            every { campaignRepository.findById(any()) } returns Optional.of(campaign)
            every { repository.findById(any()) } returns Optional.of(adGroup)
            every { repository.save(any()) } returns adGroup
            val result = service.update(1L,reqeust)
            Then("광고그룹 수정 성공") {
                result.id shouldBe 1
            }
        }

        When("광고그룹 삭제") {
            every { repository.deleteById(any()) } just runs
            service.deleteById(1L)
            Then("광고그룹 삭제 성공") {
                // Nothing to do
            }
        }
    }
})
