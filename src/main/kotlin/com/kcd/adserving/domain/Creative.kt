package com.kcd.adserving.domain

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.annotations.DynamicUpdate


@Entity
@Table(name = "creatives")
@DynamicUpdate
class Creative(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var title: String,

    var description: String,

    var textColor: String,

    var backgroundColor: String,

    var backgroundImage: String,

    var url: String,

    @ManyToOne(targetEntity = AdGroup::class, optional = false, cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var adGroup: AdGroup?,
)

