package br.edu.ifsp.scl.sc3037291.fasttripplanner

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import br.edu.ifsp.scl.sc3037291.fasttripplanner.ui.theme.FastTripPlannerTheme
import br.edu.ifsp.scl.sc3037291.fasttripplanner.utils.IntentKeys

class TripOptionsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FastTripPlannerTheme {
                TripOptionsScreen(
                    onBack = { finish() },
                    onCalculate = { hostingType, hasTransport, hasFood, hasTours ->
                        val destination = intent.getStringExtra(IntentKeys.DESTINATION) ?: ""
                        val days = intent.getIntExtra(IntentKeys.DAYS, 0)
                        val budget = intent.getDoubleExtra(IntentKeys.DAILY_BUDGET, 0.0)

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

@Composable
fun TripOptionsScreen(
    onBack: () -> Unit,
    onCalculate: (String, Boolean, Boolean, Boolean) -> Unit
) {
    var hostingType by rememberSaveable { mutableStateOf("Econômica") }
    var hasTransport by rememberSaveable { mutableStateOf(false) }
    var hasFood by rememberSaveable { mutableStateOf(false) }
    var hasTours by rememberSaveable { mutableStateOf(false) }

    Column {
        // RadioButtons para Tipo de Hospedagem
        Row {
            RadioButton(
                selected = hostingType == "Econômica",
                onClick = { hostingType = "Econômica" }
            )
            Text("Econômica")
        }
        Row {
            RadioButton(
                selected = hostingType == "Conforto",
                onClick = { hostingType = "Conforto" }
            )
            Text("Conforto")
        }
        Row {
            RadioButton(
                selected = hostingType == "Luxo",
                onClick = { hostingType = "Luxo" }
            )
            Text("Luxo")
        }

        // Checkboxes para Opções Adicionais
        Row {
            Checkbox(
                checked = hasTransport,
                onCheckedChange = { hasTransport = it }
            )
            Text("Transporte")
        }
        Row {
            Checkbox(
                checked = hasFood,
                onCheckedChange = { hasFood = it }
            )
            Text("Alimentação")
        }
        Row {
            Checkbox(
                checked = hasTours,
                onCheckedChange = { hasTours = it }
            )
            Text("Passeios")
        }

        // Botões de ação
        Button(onClick = { 
            onCalculate(hostingType, hasTransport, hasFood, hasTours) 
        }) {
            Text("Calcular")
        }
        Button(onClick = { onBack() }) {
            Text("Voltar")
        }
    }
}
