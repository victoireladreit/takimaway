package com.takimaway.application.service

import com.takimaway.infrastructure.repository.DiscountRepository
import org.springframework.stereotype.Service

@Service
class PriceCalculationService(
    private val discountRepository: DiscountRepository
) {

    /**
     * Calcule le prix total d'une réservation
     * Mutations possibles : +/-, arrondi, remise mal appliquée
     */
    fun calculatePrice(
        nights: Int,
        pricePerNight: Double,
        discountCode: String? = null
    ): Double {
        // Valider les entrées
        require(nights > 0) { "Number of nights must be positive" }
        require(pricePerNight > 0) { "Price per night must be positive" }

        // Calculer le prix de base
        val basePrice = nights * pricePerNight

        // Appliquer la remise si applicable
        val discount = if (discountCode != null) {
            getDiscountPercentage(discountCode)
        } else {
            0.0
        }

        // Calculer le prix final
        val discountAmount = basePrice * (discount / 100.0)
        val finalPrice = basePrice - discountAmount

        // Le prix ne peut jamais être négatif
        return maxOf(finalPrice, 0.0)
    }

    /**
     * Obtient le pourcentage de remise pour un code
     */
    private fun getDiscountPercentage(code: String): Double {
        val discount = discountRepository.findByCodeAndIsActiveTrue(code)
            ?: return 0.0

        return discount.percentage
    }
}