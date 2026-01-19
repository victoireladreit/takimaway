package com.takimaway.presentation.controller

import com.takimaway.presentation.mapper.RoomMapper
import com.takimaway.application.service.RoomService
import com.takimaway.domain.model.RoomType
import com.takimaway.presentation.dto.CreateRoomRequest
import com.takimaway.presentation.dto.RoomResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/rooms")
@CrossOrigin(origins = ["*"])
class RoomController(
    private val roomService: RoomService,
    private val roomMapper: RoomMapper
) {

    /**
     * GET /api/rooms
     * Récupère toutes les chambres
     */
    @GetMapping
    fun getAllRooms(): ResponseEntity<List<RoomResponse>> {
        val rooms = roomService.getAllRooms()
        return ResponseEntity.ok(roomMapper.toResponseList(rooms))
    }

    /**
     * GET /api/rooms/{id}
     * Récupère une chambre par ID
     */
    @GetMapping("/{id}")
    fun getRoomById(@PathVariable id: Long): ResponseEntity<RoomResponse> {
        val room = roomService.getRoomById(id)
        return ResponseEntity.ok(roomMapper.toResponse(room))
    }

    /**
     * GET /api/rooms/type/{roomType}
     * Récupère les chambres par type
     */
    @GetMapping("/type/{roomType}")
    fun getRoomsByType(@PathVariable roomType: RoomType): ResponseEntity<List<RoomResponse>> {
        val rooms = roomService.getAllRooms()
            .filter { it.roomType == roomType }
        return ResponseEntity.ok(roomMapper.toResponseList(rooms))
    }

    /**
     * POST /api/rooms
     * Crée une nouvelle chambre
     */
    @PostMapping
    fun createRoom(@RequestBody request: CreateRoomRequest): ResponseEntity<RoomResponse> {
        val room = roomService.createRoom(
            roomNumber = request.roomNumber,
            roomType = request.roomType,
            pricePerNight = request.pricePerNight,
            capacity = request.capacity
        )
        return ResponseEntity.status(HttpStatus.CREATED).body(roomMapper.toResponse(room))
    }
}