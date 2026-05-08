package br.edu.ifsp.scl.sc3037291.fasttripplanner

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import br.edu.ifsp.scl.sc3037291.fasttripplanner.domain.TripCostCalculator
import br.edu.ifsp.scl.sc3037291.fasttripplanner.ui.theme.FastTripPlannerTheme
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
        val isEconomicMode = intent.getBooleanExtra(IntentKeys.IS_ECONOMIC_MODE, false)

        // Processa as regras de negócio chamando o calculador de domínio isolado
        val totalCost = TripCostCalculator.calculateTotalCost(
            days = days,
            dailyBudget = dailyBudget,
            hostingType = hostingType,
            hasTransport = hasTransport,
            hasFood = hasFood,
            hasTours = hasTours,
            isEconomicMode = isEconomicMode
        )

        // Define a interface da tela passando os dados processados para o Composable
        setContent {
            FastTripPlannerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TripSummaryScreen(
                        destination = destination,
                        days = days,
                        dailyBudget = dailyBudget,
                        hostingType = hostingType,
                        hasTransport = hasTransport,
                        hasFood = hasFood,
                        hasTours = hasTours,
                        isEconomicMode = isEconomicMode,
                        totalCost = totalCost,
                        onNewTrip = {
                            // Cria Intent explícita para retornar à primeira tela
                            val nextIntent = Intent(this, TripDataActivity::class.java).apply {
                                // Adiciona flags para limpar tod o backstack (histórico)
                                // e criar uma nova tarefa, reiniciando o planejamento do zero.
                                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                            }
                            startActivity(nextIntent)
                            finish() // Encerra a Activity atual para não ficar presa na memória
                        }
                    )
                }
            }
        }
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
    isEconomicMode: Boolean,
    totalCost: Double,
    onNewTrip: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Resumo do Planejamento",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 24.dp),
            textAlign = TextAlign.Center
        )

        // Card em estilo 'Recibo': Agrupa todas as informações em uma superfície elevada
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            )
        ) {
            Column(modifier = Modifier.padding(20.dp)) {

                // Seção 1: Dados Básicos
                Text(
                    text = "Dados do Destino",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(12.dp))

                ReceiptRow(label = "Destino", value = destination)
                ReceiptRow(label = "Duração", value = "$days dias")
                ReceiptRow(label = "Orçamento Diário", value = "R$ %.2f".format(dailyBudget))

                HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

                // Seção 2: Preferências e Serviços
                Text(
                    text = "Serviços Contratados",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(12.dp))

                ReceiptRow(label = "Hospedagem", value = hostingType)
                ReceiptRow(label = "Transporte", value = if (hasTransport) "Incluso" else "Não incluso")
                ReceiptRow(label = "Alimentação", value = if (hasFood) "Inclusa" else "Não inclusa")
                ReceiptRow(label = "Passeios", value = if (hasTours) "Inclusos" else "Não inclusos")

                if (isEconomicMode) {
                    ReceiptRow(label = "Modo Econômico", value = "Ativado (-15%)")
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

                // Seção 3: Totalizadores
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = "TOTAL ESTIMADO",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "R$ %.2f".format(totalCost),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Botão de ação principal fora do Card, fixado na parte inferior
        Button(
            onClick = onNewTrip,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(top = 16.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Novo Planejamento", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}

/**
 * Componente auxiliar para renderizar uma linha de recibo padronizada.
 */
@Composable
fun ReceiptRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}