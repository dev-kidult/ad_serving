package com.kcd.adserving.domain

import com.kcd.adserving.enums.Comparison
import com.kcd.adserving.enums.TargetInclude
import com.vladmihalcea.hibernate.type.json.JsonBinaryType
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.Type
import java.math.BigDecimal
import java.time.OffsetDateTime

@Entity
@Table(name = "ad_groups")
@DynamicUpdate
class AdGroup(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var enabled: Boolean,

    var startTime: OffsetDateTime,

    var endTime: OffsetDateTime,

    var priority: Double,

    @Enumerated(EnumType.STRING)
    var classificationTargetInclude: TargetInclude? = null,

    @Type(JsonBinaryType::class)
    @Column(columnDefinition = "jsonb")
    var classificationTargets: MutableList<String>? = null,

    @Enumerated(EnumType.STRING)
    var locationSidoTargetInclude: TargetInclude? = null,

    @Type(JsonBinaryType::class)
    @Column(columnDefinition = "jsonb")
    var locationSidoTargets: MutableList<String>? = null,

    @Enumerated(EnumType.STRING)
    var monthlySalesComparison: Comparison? = null,

    var monthlySalesTargets: BigDecimal? = null,

    @ManyToOne(targetEntity = Campaign::class, optional = false, cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var campaign: Campaign?,

    @OneToMany(mappedBy = "adGroup")
    var creatives: MutableSet<Creative>?,
) {

    fun addCreative(creative: Creative) {
        this.creatives?.add(creative)
        creative.adGroup = this
    }

    fun removeCreative(creative: Creative) {
        creative.adGroup = null
        creatives?.remove(creative)
    }

    fun removeCreative(creatives: MutableIterable<Creative>) {
        creatives.forEach { removeCreative(it) }
    }
}
