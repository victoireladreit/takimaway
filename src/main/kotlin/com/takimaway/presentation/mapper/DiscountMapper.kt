package com.takimaway.presentation.mapper

import com.takimaway.domain.model.Discount
import com.takimaway.presentation.dto.DiscountResponse
import org.springframework.stereotype.Component

@Component
class DiscountMapper {

    fun toResponse(discount: Discount): DiscountResponse {
        return DiscountResponse(
            id = discount.id,
            code = discount.code,
            percentage = discount.percentage,
            isActive = discount.isActive
        )
    }

    fun toResponseList(discounts: List<Discount>): List<DiscountResponse> {
        return discounts.map { toResponse(it) }
    }
}