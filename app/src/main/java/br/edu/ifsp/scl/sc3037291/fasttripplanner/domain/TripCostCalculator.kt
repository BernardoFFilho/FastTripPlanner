package br.edu.ifsp.scl.sc3037291.fasttripplanner.domain

/**
 * Utilitário para calcular o custo total de uma viagem com base em vários parâmetros.
 * Desacoplado dos componentes de UI para melhor testabilidade e manutenção.
 */
object TripCostCalculator {

    /** Calcula o custo total da viagem de acordo com regras predefinidas.*/
    fun calculateTotalCost(
        days: Int,
        dailyBudget: Double,
        hostingType: String,
        hasTransport: Boolean,
        hasFood: Boolean,
        hasTours: Boolean,
        isEconomicMode: Boolean = false
    ): Double {
        // Aplica desconto de 15% no orçamento diário se o Modo Econômico estiver ativo
        val effectiveDailyBudget = if (isEconomicMode) dailyBudget * 0.85 else dailyBudget
        
        // custoBase = dias * orçamento (com desconto se aplicável)
        val baseCost = days * effectiveDailyBudget

        // Multiplicador: Econômica (1.0), Conforto (1.5), Luxo (2.2)
        val multiplier = when (hostingType) {
            "Luxo" -> 2.2
            "Conforto" -> 1.5
            else -> 1.0 // "Econômica" ou padrão
        }

        // Extras: Transporte (+300 fixo), Alimentação (+50 * dias), Passeios (+120 * dias)
        var extras = 0.0
        if (hasTransport) extras += 300.0
        if (hasFood) extras += 50.0 * days
        if (hasTours) extras += 120.0 * days

        return (baseCost * multiplier) + extras
    }
}
