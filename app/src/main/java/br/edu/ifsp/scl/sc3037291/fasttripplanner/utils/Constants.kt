package br.edu.ifsp.scl.sc3037291.fasttripplanner.utils

/**
 * Objeto contendo as chaves usadas para passar dados entre Activities via Intent.
 * Centralizar essas chaves melhora a manutenibilidade e evita erros de digitação.
 */
object IntentKeys {
    /** O nome do destino da viagem (String) */
    const val DESTINATION = "EXTRA_DESTINATION"

    /** Número de dias da viagem (Int) */
    const val DAYS = "EXTRA_DAYS"

    /** Valor do orçamento diário (Double) */
    const val DAILY_BUDGET = "EXTRA_DAILY_BUDGET"

    /** Tipo de hospedagem selecionada (String) */
    const val HOSTING_TYPE = "EXTRA_HOSTING_TYPE"

    /** Se o transporte está incluso (Boolean) */
    const val HAS_TRANSPORT = "EXTRA_HAS_TRANSPORT"

    /** Se a alimentação está inclusa (Boolean) */
    const val HAS_FOOD = "EXTRA_HAS_FOOD"

    /** Se os passeios estão inclusos (Boolean) */
    const val HAS_TOURS = "EXTRA_HAS_TOURS"

    /** Se o modo econômico está ativado (Boolean) */
    const val IS_ECONOMIC_MODE = "EXTRA_IS_ECONOMIC_MODE"
}