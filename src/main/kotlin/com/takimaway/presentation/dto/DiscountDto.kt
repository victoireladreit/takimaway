package com.takimaway.presentation.dto


data class DiscountResponse(
    val id: Long,
    val code: String,
    val percentage: Double,
    val isActive: Boolean
)

data class CreateDiscountRequest(
    val code: String,
    val percentage: Double,
    val isActive: Boolean? = null
)