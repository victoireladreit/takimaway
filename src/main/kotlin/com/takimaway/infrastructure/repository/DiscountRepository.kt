package com.takimaway.infrastructure.repository

import com.takimaway.domain.model.Discount
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DiscountRepository : JpaRepository<Discount, Long> {
    fun findByCodeAndIsActiveTrue(code: String): Discount?
}