package br.edu.ifsp.scl.sc3037291.fasttripplanner

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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
    onNewTrip: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Resumo da Viagem", style = MaterialTheme.typography.headlineMedium)
        Text(text = "Destino: $destination")
        Text(text = "Dias: $days")
        Text(text = "Orçamento Diário: R$ %.2f".format(dailyBudget))
        Text(text = "Tipo de Hospedagem: $hostingType")
        Text(text = "Transporte incluso: ${if (hasTransport) "Sim" else "Não"}")
        Text(text = "Alimentação inclusa: ${if (hasFood) "Sim" else "Não"}")
        Text(text = "Passeios inclusos: ${if (hasTours) "Sim" else "Não"}")
        Text(
            text = "Custo Total: R$ %.2f".format(totalCost),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(top = 16.dp)
        )

        Button(
            onClick = onNewTrip,
            modifier = Modifier.padding(top = 24.dp)
        ) {
            Text(text = "Novo Planejamento")
        }
    }
}