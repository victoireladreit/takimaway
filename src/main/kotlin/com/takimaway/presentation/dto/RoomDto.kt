package com.takimaway.presentation.dto

import com.takimaway.domain.model.RoomType

data class RoomResponse(
    val id: Long,
    val roomNumber: String,
    val roomType: RoomType,
    val pricePerNight: Double,
    val capacity: Int
)

data class CreateRoomRequest(
    val roomNumber: String,
    val roomType: RoomType,
    val pricePerNight: Double,
    val capacity: Int
)