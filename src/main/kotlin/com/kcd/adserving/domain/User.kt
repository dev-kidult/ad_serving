package com.kcd.adserving.domain

import com.kcd.adserving.enums.Gender
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.DynamicUpdate
import java.math.BigDecimal

@Entity
@Table(name = "users")
@DynamicUpdate
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var age: Int,

    @Enumerated(EnumType.STRING)
    var gender: Gender,

    var name: String,

    var classificationName: String,

    var locationSidoName: String,

    var monthlySales: BigDecimal,
)
