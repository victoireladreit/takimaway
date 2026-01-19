package com.takimaway.application.service

import com.takimaway.domain.exception.Exceptions
import com.takimaway.domain.model.Room
import com.takimaway.domain.model.RoomType
import com.takimaway.infrastructure.repository.RoomRepository
import org.springframework.stereotype.Service

@Service
class RoomService(
    private val roomRepository: RoomRepository
) {

    fun getAllRooms(): List<Room> = roomRepository.findAll()

    fun getRoomById(id: Long): Room = roomRepository.findById(id)
        .orElseThrow { Exceptions.RoomNotFoundException(id) }

    fun createRoom(
        roomNumber: String,
        roomType: RoomType,
        pricePerNight: Double,
        capacity: Int
    ): Room {
        require(pricePerNight > 0) { "Price must be positive" }
        require(capacity > 0) { "Capacity must be positive" }

        val room = Room(
            roomNumber = roomNumber,
            roomType = roomType,
            pricePerNight = pricePerNight,
            capacity = capacity
        )

        return roomRepository.save(room)
    }
}