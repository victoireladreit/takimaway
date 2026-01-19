package com.takimaway.presentation.mapper

import com.takimaway.domain.model.Reservation
import com.takimaway.presentation.dto.ReservationResponse
import org.springframework.stereotype.Component

@Component
class ReservationMapper {

    fun toResponse(reservation: Reservation): ReservationResponse {
        return ReservationResponse(
            id = reservation.id,
            roomNumber = reservation.room.roomNumber,
            guestName = reservation.guestName,
            guestEmail = reservation.guestEmail,
            checkInDate = reservation.checkInDate,
            checkOutDate = reservation.checkOutDate,
            totalPrice = reservation.totalPrice,
            status = reservation.status.name
        )
    }

    fun toResponseList(reservations: List<Reservation>): List<ReservationResponse> {
        return reservations.map { toResponse(it) }
    }
}