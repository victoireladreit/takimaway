package com.takimaway.presentation.dto

import java.time.LocalDate

data class ReservationResponse(
    val id: Long,
    val roomNumber: String,
    val guestName: String,
    val guestEmail: String,
    val checkInDate: LocalDate,
    val checkOutDate: LocalDate,
    val totalPrice: Double,
    val status: String
)

data class CreateReservationRequest(
    val roomId: Long,
    val guestName: String,
    val guestEmail: String,
    val checkInDate: LocalDate,
    val checkOutDate: LocalDate,
    val discountCode: String? = null
)