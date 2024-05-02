package com.kcd.adserving.repository

import com.kcd.adserving.domain.AdGroup
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.stereotype.Repository

@Repository
interface AdGroupRepository : JpaRepository<AdGroup, Long>, QuerydslPredicateExecutor<AdGroup>
