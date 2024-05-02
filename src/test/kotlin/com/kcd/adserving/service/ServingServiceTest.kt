package com.kcd.adserving.service

import com.kcd.adserving.domain.AdGroup
import com.kcd.adserving.domain.Campaign
import com.kcd.adserving.domain.Creative
import com.kcd.adserving.domain.QAdGroup
import com.kcd.adserving.domain.QCampaign
import com.kcd.adserving.domain.QCreative
import com.kcd.adserving.domain.User
import com.kcd.adserving.enums.Gender
import com.kcd.adserving.enums.TargetInclude
import com.kcd.adserving.mapper.CreativeMapper
import com.kcd.adserving.mapper.CreativeMapperImpl
import com.ninjasquad.springmockk.MockkBean
import com.querydsl.core.types.EntityPath
import com.querydsl.core.types.Predicate
import com.querydsl.jpa.impl.JPAQuery
import com.querydsl.jpa.impl.JPAQueryFactory
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.math.BigDecimal
import java.time.OffsetDateTime

@SpringBootTest(classes = [ServingService::class, CreativeMapperImpl::class])
class ServingServiceTest(
    @Autowired private val service: ServingService,
    @Autowired private val mapper: CreativeMapper,
    @MockkBean private val userService: UserService,
    @MockkBean private val queryFactory: JPAQueryFactory,
    @MockkBean private val query: JPAQuery<Campaign>,
) : BehaviorSpec({

    val user = User(
        id = 1L,
        age = 34,
        gender = Gender.MALE,
        name = "정용희",
        classificationName = "개발자",
        locationSidoName = "경기도",
        monthlySales = BigDecimal(1000000),
    )

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
        classificationTargets = mutableListOf("개발자"),
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

    beforeTest {
        campaign.addAdGroup(adGroup)
        adGroup.addCreative(creative)
    }

    Given("광고 서빙 서비스에서") {
        When("광고 서빙을 요청") {
            every { userService.getById(any()) } returns user
            every { queryFactory.select(any<QCampaign>()) } returns query
            every { query.from(any()) } returns query
            every { query.leftJoin(QCampaign.campaign.adGroups, QAdGroup.adGroup) } returns query
            every { query.leftJoin(QAdGroup.adGroup.creatives, QCreative.creative) } returns query
            every { query.fetchJoin() } returns query
            every { query.where(any<Predicate>(), any<Predicate>(), any<Predicate>(), any<Predicate>()) } returns query
            every { query.orderBy(any()) } returns query
            every { query.fetch() } returns listOf(campaign)

            val result = service.recommend(1L, listOf("placement"))

            Then("광고 서빙 성공") {
                result[0].name shouldBe "placement"
                result[0].creatives[0].title shouldBe "title"
            }
        }
    }
})
