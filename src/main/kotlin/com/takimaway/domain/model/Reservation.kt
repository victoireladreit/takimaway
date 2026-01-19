package com.takimaway.domain.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "reservations")
data class Reservation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    val room: Room,

    val guestName: String,

    val guestEmail: String,

    val checkInDate: LocalDate,

    val checkOutDate: LocalDate,

    val totalPrice: Double,

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "takimaway.reservation_status")
    var status: ReservationStatus = ReservationStatus.CONFIRMED,

    val createdAt: LocalDateTime = LocalDateTime.now()
)

enum class ReservationStatus {
    PENDING, CONFIRMED, CANCELLED
}