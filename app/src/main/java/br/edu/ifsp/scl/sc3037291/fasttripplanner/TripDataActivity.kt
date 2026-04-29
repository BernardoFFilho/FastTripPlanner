package br.edu.ifsp.scl.sc3037291.fasttripplanner

import android.os.Bundle
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
import br.edu.ifsp.scl.sc3037291.fasttripplanner.ui.theme.FastTripPlannerTheme

class TripDataActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FastTripPlannerTheme {
                TripDataScreen()
            }
        }
    }
}

@Composable
fun TripDataScreen() {
    var destination by rememberSaveable { mutableStateOf("") }
    var days by rememberSaveable { mutableStateOf("") }
    var budget by rememberSaveable { mutableStateOf("") }

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
        Button(onClick = { /* TODO: Avançar */ }) {
            Text("Avançar")
        }
    }
}
