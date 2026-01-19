package com.takimaway.presentation.mapper

import com.takimaway.domain.model.Room
import com.takimaway.presentation.dto.RoomResponse
import org.springframework.stereotype.Component

@Component
class RoomMapper {

    fun toResponse(room: Room): RoomResponse {
        return RoomResponse(
            id = room.id,
            roomNumber = room.roomNumber,
            roomType = room.roomType,
            pricePerNight = room.pricePerNight,
            capacity = room.capacity
        )
    }

    fun toResponseList(rooms: List<Room>): List<RoomResponse> {
        return rooms.map { toResponse(it) }
    }
}