package br.edu.ifsp.scl.sc3037291.fasttripplanner

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import br.edu.ifsp.scl.sc3037291.fasttripplanner.ui.theme.FastTripPlannerTheme
import br.edu.ifsp.scl.sc3037291.fasttripplanner.utils.IntentKeys

class TripOptionsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FastTripPlannerTheme {
                // Surface: Garante que o fundo combine com o tema (Preto no Dark, Branco no Light)
                // seguindo o padrão estabelecido na TripDataActivity.
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TripOptionsScreen(
                        onBack = { finish() }, // Encerra a activity atual e volta para a tela 1
                        onCalculate = { hostingType, hasTransport, hasFood, hasTours ->
                            // Recupera os dados enviados pela Tela 1 via Intent
                            val destination = intent.getStringExtra(IntentKeys.DESTINATION) ?: ""
                            val days = intent.getIntExtra(IntentKeys.DAYS, 0)
                            val budget = intent.getDoubleExtra(IntentKeys.DAILY_BUDGET, 0.0)

                            // Cria uma Intent explícita para a Tela 3 combinando todos os dados coletados
                            val summaryIntent = Intent(this, TripSummaryActivity::class.java).apply {
                                putExtra(IntentKeys.DESTINATION, destination)
                                putExtra(IntentKeys.DAYS, days)
                                putExtra(IntentKeys.DAILY_BUDGET, budget)
                                putExtra(IntentKeys.HOSTING_TYPE, hostingType)
                                putExtra(IntentKeys.HAS_TRANSPORT, hasTransport)
                                putExtra(IntentKeys.HAS_FOOD, hasFood)
                                putExtra(IntentKeys.HAS_TOURS, hasTours)
                            }
                            startActivity(summaryIntent)
                        }
                    )
                }
            }
        }
    }
}

/**
 * Composable que renderiza a interface da Tela 2 com Material Design 3.
 */
@Composable
fun TripOptionsScreen(
    onBack: () -> Unit,
    onCalculate: (String, Boolean, Boolean, Boolean) -> Unit
) {
    var hostingType by rememberSaveable { mutableStateOf("Econômica") }
    var hasTransport by rememberSaveable { mutableStateOf(false) }
    var hasFood by rememberSaveable { mutableStateOf(false) }
    var hasTours by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título centralizado para consistência visual com a proposta de UI
        Text(
            text = "Personalize sua Viagem",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        // RadioButtons para Tipo de Hospedagem
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Tipo de Hospedagem",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Lista de Opções (RadioButtons)
                val options = listOf("Econômica", "Conforto", "Luxo")
                options.forEach { option ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { hostingType = option }
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (hostingType == option),
                            onClick = { hostingType = option }
                        )
                        Text(text = option, style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        }

        // Checkboxes para Opções Adicionais
        // UX: Uso de Card para separar logicamente as opções de serviços
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Serviços Adicionais",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Opção Transporte
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { hasTransport = !hasTransport }
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = hasTransport,
                        onCheckedChange = { hasTransport = it }
                    )
                    Text(text = "Transporte", style = MaterialTheme.typography.bodyLarge)
                }

                // Opção Alimentação
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { hasFood = !hasFood }
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = hasFood,
                        onCheckedChange = { hasFood = it }
                    )
                    Text(text = "Alimentação", style = MaterialTheme.typography.bodyLarge)
                }

                // Opção Passeios
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { hasTours = !hasTours }
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = hasTours,
                        onCheckedChange = { hasTours = it }
                    )
                    Text(text = "Passeios", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Botões de ação
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedButton(
                onClick = { onBack() },
                modifier = Modifier.weight(1f),
                shape = MaterialTheme.shapes.medium
            ) {
                Text("Voltar")
            }

            Button(
                onClick = { onCalculate(hostingType, hasTransport, hasFood, hasTours) },
                modifier = Modifier.weight(1f),
                shape = MaterialTheme.shapes.medium
            ) {
                Text("Calcular")
            }
        }
    }
}
