package br.edu.ifsp.scl.sc3037291.fasttripplanner.domain

/**
 * Utility for calculating the total cost of a trip based on various parameters.
 * Decoupled from UI components for better testability and maintenance.
 */
object TripCostCalculator {

    /** Calculates the total trip cost according to predefined rules.*/
    fun calculateTotalCost(
        days: Int,
        dailyBudget: Double,
        hostingType: String,
        hasTransport: Boolean,
        hasFood: Boolean,
        hasTours: Boolean
    ): Double {
        // baseCost = days * budget
        val baseCost = days * dailyBudget

        // Multiplier: Econômica (1.0), Conforto (1.5), Luxo (2.2)
        val multiplier = when (hostingType) {
            "Luxo" -> 2.2
            "Conforto" -> 1.5
            else -> 1.0 // "Econômica" or default
        }

        // Extras: Transport (+300 fixed), Food (+50 * days), Tours (+120 * days)
        var extras = 0.0
        if (hasTransport) extras += 300.0
        if (hasFood) extras += 50.0 * days
        if (hasTours) extras += 120.0 * days

        return (baseCost * multiplier) + extras
    }
}
