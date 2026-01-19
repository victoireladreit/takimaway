package com.takimaway.infrastructure.repository

import com.takimaway.domain.model.Discount
import com.takimaway.domain.model.Reservation
import com.takimaway.domain.model.Room
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface ReservationRepository : JpaRepository<Reservation, Long> {

    @Query("""
        SELECT r FROM Reservation r 
        WHERE r.room.id = :roomId 
        AND r.status = 'CONFIRMED'
        AND (
            (r.checkInDate < :endDate AND r.checkOutDate > :startDate)
        )
    """)
    fun findReservationsInDateRange(
        @Param("roomId") roomId: Long,
        @Param("startDate") startDate: LocalDate,
        @Param("endDate") endDate: LocalDate
    ): List<Reservation>

    @Query("""
        SELECT r FROM Reservation r 
        WHERE r.room.id = :roomId 
        AND r.status = 'CONFIRMED'
    """)
    fun findConfirmedReservationsByRoom(
        @Param("roomId") roomId: Long
    ): List<Reservation>
}
