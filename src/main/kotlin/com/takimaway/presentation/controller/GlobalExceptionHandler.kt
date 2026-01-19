package com.takimaway.presentation.controller

import com.takimaway.presentation.dto.ErrorResponse
import com.takimaway.domain.exception.Exceptions
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import java.time.LocalDateTime

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(Exceptions.RoomNotFoundException::class)
    fun handleRoomNotFound(ex: Exceptions.RoomNotFoundException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(
            timestamp = LocalDateTime.now(),
            status = HttpStatus.NOT_FOUND.value(),
            error = "Room Not Found",
            message = ex.message ?: "The requested room does not exist",
            path = request.getDescription(false).replace("uri=", "")
        )
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error)
    }

    @ExceptionHandler(Exceptions.ReservationNotFoundException::class)
    fun handleReservationNotFound(ex: Exceptions.ReservationNotFoundException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(
            timestamp = LocalDateTime.now(),
            status = HttpStatus.NOT_FOUND.value(),
            error = "Reservation Not Found",
            message = ex.message ?: "The requested reservation does not exist",
            path = request.getDescription(false).replace("uri=", "")
        )
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error)
    }

    @ExceptionHandler(Exceptions.RoomNotAvailableException::class)
    fun handleRoomNotAvailable(ex: Exceptions.RoomNotAvailableException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(
            timestamp = LocalDateTime.now(),
            status = HttpStatus.CONFLICT.value(),
            error = "Room Not Available",
            message = ex.message ?: "The room is not available for the requested dates",
            path = request.getDescription(false).replace("uri=", "")
        )
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error)
    }

    @ExceptionHandler(Exceptions.ReservationAlreadyCancelledException::class)
    fun handleReservationAlreadyCancelled(ex: Exceptions.ReservationAlreadyCancelledException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(
            timestamp = LocalDateTime.now(),
            status = HttpStatus.BAD_REQUEST.value(),
            error = "Invalid Operation",
            message = ex.message ?: "This reservation is already cancelled",
            path = request.getDescription(false).replace("uri=", "")
        )
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgument(ex: IllegalArgumentException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(
            timestamp = LocalDateTime.now(),
            status = HttpStatus.BAD_REQUEST.value(),
            error = "Invalid Input",
            message = ex.message ?: "The provided input is invalid",
            path = request.getDescription(false).replace("uri=", "")
        )
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error)
    }

    @ExceptionHandler(Exception::class)
    fun handleGlobalException(ex: Exception, request: WebRequest): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(
            timestamp = LocalDateTime.now(),
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            error = "Internal Server Error",
            message = ex.message ?: "An unexpected error occurred",
            path = request.getDescription(false).replace("uri=", "")
        )
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error)
    }
}