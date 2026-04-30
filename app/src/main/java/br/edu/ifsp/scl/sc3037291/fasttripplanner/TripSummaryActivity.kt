package br.edu.ifsp.scl.sc3037291.fasttripplanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.edu.ifsp.scl.sc3037291.fasttripplanner.domain.TripCostCalculator
import br.edu.ifsp.scl.sc3037291.fasttripplanner.utils.IntentKeys

class TripSummaryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Recupera os dados da Intent explícita enviados pela TripOptionsActivity.
        // Utiliza as chaves estáticas definidas em IntentKeys para evitar erros de digitação.
        // Em caso de ausência do dado, define valores padrão seguros (fallback).
        val destination = intent.getStringExtra(IntentKeys.DESTINATION) ?: ""
        val days = intent.getIntExtra(IntentKeys.DAYS, 0)
        val dailyBudget = intent.getDoubleExtra(IntentKeys.DAILY_BUDGET, 0.0)
        val hostingType = intent.getStringExtra(IntentKeys.HOSTING_TYPE) ?: ""
        val hasTransport = intent.getBooleanExtra(IntentKeys.HAS_TRANSPORT, false)
        val hasFood = intent.getBooleanExtra(IntentKeys.HAS_FOOD, false)
        val hasTours = intent.getBooleanExtra(IntentKeys.HAS_TOURS, false)

        // Processa as regras de negócio chamando o calculador de domínio isolado
        val totalCost = TripCostCalculator.calculateTotalCost(
            days = days,
            dailyBudget = dailyBudget,
            hostingType = hostingType,
            hasTransport = hasTransport,
            hasFood = hasFood,
            hasTours = hasTours
        )
    }
}
/**
 * Composable responsável por renderizar a interface visual do resumo da viagem.
 * É um componente "burro" (stateless), que apenas recebe os dados formatados e os exibe.
 */
@Composable
fun TripSummaryScreen(
    destination: String,
    days: Int,
    dailyBudget: Double,
    hostingType: String,
    hasTransport: Boolean,
    hasFood: Boolean,
    hasTours: Boolean,
    totalCost: Double,
    onNewTrip: () -> Unit // Callback executado quando o usuário clica em "Novo Planejamento"
) {
    // Coluna simples para organizar os elementos verticalmente (interface crua)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Título da tela
        Text(text = "Resumo da Viagem", style = MaterialTheme.typography.headlineMedium)

        // Exibição dos dados inseridos pelo usuário
        Text(text = "Destino: $destination")
        Text(text = "Dias: $days")
        Text(text = "Orçamento Diário: R$ %.2f".format(dailyBudget))
        Text(text = "Tipo de Hospedagem: $hostingType")

        // Exibição dos serviços adicionais (convertendo Boolean para texto amigável)
        Text(text = "Transporte incluso: ${if (hasTransport) "Sim" else "Não"}")
        Text(text = "Alimentação inclusa: ${if (hasFood) "Sim" else "Não"}")
        Text(text = "Passeios inclusos: ${if (hasTours) "Sim" else "Não"}")

        // Exibição do valor total calculado, com destaque visual
        Text(
            text = "Custo Total: R$ %.2f".format(totalCost),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(top = 16.dp)
        )

        // Botão para reiniciar o fluxo do aplicativo
        Button(
            onClick = onNewTrip,
            modifier = Modifier.padding(top = 24.dp)
        ) {
            Text(text = "Novo Planejamento")
        }
    }
}