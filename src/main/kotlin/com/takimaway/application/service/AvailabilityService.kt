package com.takimaway.application.service

import com.takimaway.domain.model.Reservation
import com.takimaway.domain.model.Room
import com.takimaway.infrastructure.repository.ReservationRepository
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class AvailabilityService(
    private val reservationRepository: ReservationRepository
) {

    /**
     * Vérifie si une chambre est disponible pour les dates données
     * C'EST CETTE FONCTION QUI A LES BUGS POUR LA DÉMO
     */
    fun isRoomAvailable(
        room: Room,
        checkInDate: LocalDate,
        checkOutDate: LocalDate
    ): Boolean {
        val existingReservations = reservationRepository
            .findConfirmedReservationsByRoom(room.id!!)

        // Vérifier qu'il n'y a pas de chevauchement
        for (reservation in existingReservations) {
            if (datesOverlap(checkInDate, checkOutDate, reservation)) {
                return false
            }
        }

        return true
    }

    /**
     * Vérifie si deux périodes se chevauchent
     * Mutations possibles : < -> <=, > -> >=, && -> ||
     */
    private fun datesOverlap(
        newStart: LocalDate,
        newEnd: LocalDate,
        existingReservation: Reservation
    ): Boolean {
        // new:       |--------|
        // existing: |--------|
        // Overlap si new.start < existing.end ET new.end > existing.start
        return newStart < existingReservation.checkOutDate &&
                newEnd > existingReservation.checkInDate
    }

    /**
     * Récupère les dates disponibles d'une chambre
     */
    fun getAvailableDates(
        room: Room,
        startDate: LocalDate,
        numDays: Int
    ): List<LocalDate> {
        val availableDates = mutableListOf<LocalDate>()
        var currentDate = startDate

        repeat(numDays) {
            if (isRoomAvailable(room, currentDate, currentDate.plusDays(1))) {
                availableDates.add(currentDate)
            }
            currentDate = currentDate.plusDays(1)
        }

        return availableDates
    }
}