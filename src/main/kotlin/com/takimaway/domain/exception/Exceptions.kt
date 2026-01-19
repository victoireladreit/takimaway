package com.takimaway.domain.exception

import java.time.LocalDate

class Exceptions {
    class RoomNotFoundException(roomId: Long) :
        TakimawayException("Room with ID $roomId not found")

    class ReservationNotFoundException(reservationId: Long) :
        TakimawayException("Reservation with ID $reservationId not found")

    class RoomNotAvailableException(roomId: Long, startDate: LocalDate, endDate: LocalDate) :
        TakimawayException("Room $roomId is not available from $startDate to $endDate")

    class ReservationAlreadyCancelledException(reservationId: Long) :
        TakimawayException("Reservation $reservationId is already cancelled")

    class InvalidDateRangeException(message: String) :
        TakimawayException(message)
}