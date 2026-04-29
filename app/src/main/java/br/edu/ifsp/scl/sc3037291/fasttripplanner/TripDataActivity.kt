package br.edu.ifsp.scl.sc3037291.fasttripplanner

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import br.edu.ifsp.scl.sc3037291.fasttripplanner.ui.theme.FastTripPlannerTheme
import br.edu.ifsp.scl.sc3037291.fasttripplanner.utils.IntentKeys

class TripDataActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FastTripPlannerTheme {
                TripDataScreen { destination, days, budget ->
                    val intent = Intent(this, TripOptionsActivity::class.java).apply {
                        putExtra(IntentKeys.DESTINATION, destination)
                        putExtra(IntentKeys.DAYS, days)
                        putExtra(IntentKeys.DAILY_BUDGET, budget)
                    }
                    startActivity(intent)
                }
            }
        }
    }
}

@Composable
fun TripDataScreen(onNavigateNext: (String, Int, Double) -> Unit) {
    var destination by rememberSaveable { mutableStateOf("") }
    var days by rememberSaveable { mutableStateOf("") }
    var budget by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current

    Column {
        TextField(
            value = destination,
            onValueChange = { destination = it },
            label = { Text("Destino") }
        )
        TextField(
            value = days,
            onValueChange = { days = it },
            label = { Text("Dias") }
        )
        TextField(
            value = budget,
            onValueChange = { budget = it },
            label = { Text("Orçamento") }
        )
        Button(onClick = {
            val daysInt = days.toIntOrNull()
            val budgetDouble = budget.toDoubleOrNull()

            if (destination.isNotBlank() &&
                daysInt != null && daysInt > 0 &&
                budgetDouble != null && budgetDouble > 0
            ) {
                onNavigateNext(destination, daysInt, budgetDouble)
            } else {
                Toast.makeText(context, "Por favor, preencha todos os campos corretamente.", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text("Avançar")
        }
    }
}
