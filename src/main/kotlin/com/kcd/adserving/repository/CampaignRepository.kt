package com.kcd.adserving.repository

import com.kcd.adserving.domain.Campaign
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.stereotype.Repository

@Repository
interface CampaignRepository : JpaRepository<Campaign, Long>, QuerydslPredicateExecutor<Campaign>
