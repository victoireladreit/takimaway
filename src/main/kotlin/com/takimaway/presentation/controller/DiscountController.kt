package com.takimaway.presentation.controller

import com.takimaway.presentation.mapper.DiscountMapper
import com.takimaway.application.service.DiscountService
import com.takimaway.presentation.dto.CreateDiscountRequest
import com.takimaway.presentation.dto.DiscountResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/discounts")
@CrossOrigin(origins = ["*"])
class DiscountController(
    private val discountService: DiscountService,
    private val discountMapper: DiscountMapper
) {

    /**
     * GET /api/discounts
     * Récupère tous les codes de remise actifs
     */
    @GetMapping
    fun getAllDiscounts(): ResponseEntity<List<DiscountResponse>> {
        val discounts = discountService.getAllActiveDiscounts()
        return ResponseEntity.ok(discountMapper.toResponseList(discounts))
    }

    /**
     * GET /api/discounts/{code}
     * Récupère un code de remise spécifique
     */
    @GetMapping("/{code}")
    fun getDiscountByCode(@PathVariable code: String): ResponseEntity<DiscountResponse> {
        val discount = discountService.getDiscountByCode(code)
        return ResponseEntity.ok(discountMapper.toResponse(discount))
    }

    /**
     * POST /api/discounts
     * Crée un nouveau code de remise
     */
    @PostMapping
    fun createDiscount(@RequestBody request: CreateDiscountRequest): ResponseEntity<DiscountResponse> {
        val discount = discountService.createDiscount(
            code = request.code,
            percentage = request.percentage,
            isActive = request.isActive ?: true
        )
        return ResponseEntity.status(HttpStatus.CREATED).body(discountMapper.toResponse(discount))
    }
}