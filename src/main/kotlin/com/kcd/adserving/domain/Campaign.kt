package com.kcd.adserving.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.hibernate.annotations.DynamicUpdate

@Entity
@Table(name = "campaigns")
@DynamicUpdate
class Campaign(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var placement: String,

    @OneToMany(mappedBy = "campaign")
    var adGroups: MutableSet<AdGroup>?,
) {

    fun addAdGroup(adGroup: AdGroup) {
        this.adGroups?.add(adGroup)
        adGroup.campaign = this
    }

    fun removeAdGroup(adGroup: AdGroup) {
        adGroup.campaign = null
        adGroups?.remove(adGroup)
    }

    fun removeAdGroups(adGroups: MutableIterable<AdGroup>) {
        adGroups.forEach { removeAdGroup(it) }
    }
}
