package com.takimaway.presentation.controller

import com.takimaway.presentation.mapper.ReservationMapper
import com.takimaway.application.service.ReservationService
import com.takimaway.presentation.dto.CreateReservationRequest
import com.takimaway.presentation.dto.ReservationResponse
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/api/reservations")
@CrossOrigin(origins = ["*"])
class ReservationController(
    private val reservationService: ReservationService,
    private val reservationMapper: ReservationMapper
) {

    /**
     * POST /api/reservations
     * Crée une nouvelle réservation
     */
    @PostMapping
    fun createReservation(@RequestBody request: CreateReservationRequest): ResponseEntity<ReservationResponse> {
        val reservation = reservationService.createReservation(
            roomId = request.roomId,
            guestName = request.guestName,
            guestEmail = request.guestEmail,
            checkInDate = request.checkInDate,
            checkOutDate = request.checkOutDate,
            discountCode = request.discountCode
        )
        return ResponseEntity.status(HttpStatus.CREATED).body(reservationMapper.toResponse(reservation))
    }

    /**
     * GET /api/reservations/{id}
     * Récupère une réservation par ID
     */
    @GetMapping("/{id}")
    fun getReservationById(@PathVariable id: Long): ResponseEntity<ReservationResponse> {
        // À implémenter dans le service si nécessaire
        return ResponseEntity.notFound().build()
    }

    /**
     * GET /api/reservations/room/{roomId}
     * Récupère toutes les réservations d'une chambre
     */
    @GetMapping("/room/{roomId}")
    fun getReservationsByRoom(
        @PathVariable roomId: Long,
        @RequestParam(required = false)
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        startDate: LocalDate?,
        @RequestParam(required = false)
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        endDate: LocalDate?
    ): ResponseEntity<List<ReservationResponse>> {
        val start = startDate ?: LocalDate.now().minusDays(30)
        val end = endDate ?: LocalDate.now().plusDays(90)

        val reservations = reservationService.getReservationsForRoom(roomId, start, end)
        return ResponseEntity.ok(reservationMapper.toResponseList(reservations))
    }

    /**
     * PATCH /api/reservations/{id}/cancel
     * Annule une réservation
     */
    @PatchMapping("/{id}/cancel")
    fun cancelReservation(@PathVariable id: Long): ResponseEntity<ReservationResponse> {
        val reservation = reservationService.cancelReservation(id)
        return ResponseEntity.ok(reservationMapper.toResponse(reservation))
    }
}