package com.takimaway.application.service

import com.takimaway.domain.model.Discount
import com.takimaway.infrastructure.repository.DiscountRepository
import org.springframework.stereotype.Service

@Service
class DiscountService(
    private val discountRepository: DiscountRepository
) {

    fun getAllActiveDiscounts(): List<Discount> {
        return discountRepository.findAll()
            .filter { it.isActive }
    }

    fun getDiscountByCode(code: String): Discount {
        return discountRepository.findByCodeAndIsActiveTrue(code)
            ?: throw IllegalArgumentException("Discount code '$code' not found or inactive")
    }

    fun createDiscount(code: String, percentage: Double, isActive: Boolean = true): Discount {
        require(percentage in 0.0..100.0) { "Percentage must be between 0 and 100" }
        require(code.isNotBlank()) { "Discount code cannot be empty" }

        val discount = Discount(
            code = code.uppercase(),
            percentage = percentage,
            isActive = isActive
        )

        return discountRepository.save(discount)
    }
}