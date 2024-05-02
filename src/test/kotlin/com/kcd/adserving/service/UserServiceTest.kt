package com.kcd.adserving.service

import com.kcd.adserving.domain.User
import com.kcd.adserving.dto.UserDto
import com.kcd.adserving.enums.Gender
import com.kcd.adserving.mapper.UserMapper
import com.kcd.adserving.mapper.UserMapperImpl
import com.kcd.adserving.repository.UserRepository
import com.ninjasquad.springmockk.MockkBean
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.just
import io.mockk.runs
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.math.BigDecimal
import java.util.Optional

@SpringBootTest(classes = [UserService::class, UserMapperImpl::class])
internal class UserServiceTest(
    @Autowired private val service: UserService,
    @Autowired private val mapper: UserMapper,
    @MockkBean private val repository: UserRepository,
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

    val request = UserDto.Request(
        age = 34,
        gender = Gender.MALE,
        name = "정용희",
        classificationName = "개발자",
        locationSidoName = "경기도",
        monthlySales = BigDecimal(1000000),
    )

    Given("유저 서비스에서") {
        When("유저 전체 조회") {
            every { repository.findAll() } returns mutableListOf(user)
            val result = service.getAll()
            Then("유저 전체 조회 성공") {
                result.size shouldBe 1
            }
        }

        When("유저 단건 조회") {
            every { repository.findById(1L) } returns Optional.of(user)
            val result = service.getById(1L)
            Then("유저 조회 성공") {
                result.id shouldBe 1
            }
        }

        When("유저 단건 조회 실패") {
            every { repository.findById(1L) } returns Optional.empty()
            val result = shouldThrow<IllegalArgumentException> { service.getById(1L) }
            Then("유저 조회 실패") {
                result.message shouldBe "user not found"
            }
        }

        When("유저 생성 정상 Request") {
            every { repository.save(any()) } returns user
            val result = service.create(request)
            Then("유저 생성 성공") {
                result.id shouldBe 1
            }
        }

        When("유저 수정") {
            every { repository.findById(1L) } returns Optional.of(user)
            every { repository.save(any()) } returns user
            val result = service.update(1L, request)
            Then("유저 수정 성공") {
                result.id shouldBe 1
            }
        }

        When("유저 삭제") {
            every { repository.deleteById(1L) } just runs
            service.deleteById(1L)
            Then("유저 삭제 성공") {
                // Nothing to do
            }
        }
    }
})
