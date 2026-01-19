package com.takimaway.application.service

import com.takimaway.domain.exception.Exceptions
import com.takimaway.domain.model.Reservation
import com.takimaway.domain.model.ReservationStatus
import com.takimaway.infrastructure.repository.ReservationRepository
import com.takimaway.infrastructure.repository.RoomRepository
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ReservationService(
    private val reservationRepository: ReservationRepository,
    private val roomRepository: RoomRepository,
    private val availabilityService: AvailabilityService,
    private val priceCalculationService: PriceCalculationService
) {

    /**
     * Crée une nouvelle réservation après validation
     */
    fun createReservation(
        roomId: Long,
        guestName: String,
        guestEmail: String,
        checkInDate: LocalDate,
        checkOutDate: LocalDate,
        discountCode: String? = null
    ): Reservation {
        // Validation des dates
        validateDates(checkInDate, checkOutDate)

        // Vérifier la disponibilité
        val room = roomRepository.findById(roomId)
            .orElseThrow { Exceptions.RoomNotFoundException(roomId) }

        if (!availabilityService.isRoomAvailable(room, checkInDate, checkOutDate)) {
            throw Exceptions.RoomNotAvailableException(roomId, checkInDate, checkOutDate)
        }

        // Calculer le prix
        val nights = calculateNights(checkInDate, checkOutDate)
        val totalPrice = priceCalculationService.calculatePrice(
            nights = nights,
            pricePerNight = room.pricePerNight,
            discountCode = discountCode
        )

        // Créer et sauvegarder la réservation
        val reservation = Reservation(
            room = room,
            guestName = guestName,
            guestEmail = guestEmail,
            checkInDate = checkInDate,
            checkOutDate = checkOutDate,
            totalPrice = totalPrice,
            status = ReservationStatus.CONFIRMED
        )

        return reservationRepository.save(reservation)
    }

    /**
     * Annule une réservation existante
     */
    fun cancelReservation(reservationId: Long): Reservation {
        val reservation = reservationRepository.findById(reservationId)
            .orElseThrow { Exceptions.ReservationNotFoundException(reservationId) }

        if (reservation.status == ReservationStatus.CANCELLED) {
            throw Exceptions.ReservationAlreadyCancelledException(reservationId)
        }

        reservation.status = ReservationStatus.CANCELLED
        return reservationRepository.save(reservation)
    }

    /**
     * Récupère toutes les réservations d'une chambre pour une période
     */
    fun getReservationsForRoom(
        roomId: Long,
        startDate: LocalDate,
        endDate: LocalDate
    ): List<Reservation> {
        return reservationRepository.findReservationsInDateRange(roomId, startDate, endDate)
    }

    private fun validateDates(checkInDate: LocalDate, checkOutDate: LocalDate) {
        require(checkInDate.isBefore(checkOutDate)) {
            "Check-in date must be before check-out date"
        }
        require(checkInDate >= LocalDate.now()) {
            "Check-in date cannot be in the past"
        }
    }

    private fun calculateNights(startDate: LocalDate, endDate: LocalDate): Int {
        return (endDate.toEpochDay() - startDate.toEpochDay()).toInt()
    }
}