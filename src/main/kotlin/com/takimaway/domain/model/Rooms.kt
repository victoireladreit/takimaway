package com.takimaway.domain.model

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "rooms")
data class Room(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val roomNumber: String,

    @Enumerated(EnumType.STRING)
    val roomType: RoomType,

    val pricePerNight: Double,

    val capacity: Int
)

enum class RoomType {
    BASIC, DOUBLE, SUITE
}