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

/**
 * Activity inicial do aplicativo (Tela 1).
 * Responsável por coletar os dados básicos da viagem.
 */
class TripDataActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Define o conteúdo visual da Activity usando Jetpack Compose
        setContent {
            FastTripPlannerTheme {
                // Chama o Composable da tela passando a ação de navegação como callback
                TripDataScreen(
                    onNavigateNext = { destination, days, budget ->
                        // Cria uma Intent explícita para navegar para a Tela 2 (TripOptionsActivity)
                        val intent = Intent(this, TripOptionsActivity::class.java).apply {
                            // Adiciona os dados validados na Intent utilizando as chaves padronizadas
                            putExtra(IntentKeys.DESTINATION, destination)
                            putExtra(IntentKeys.DAYS, days)
                            putExtra(IntentKeys.DAILY_BUDGET, budget)
                        }
                        // Inicia a próxima Activity
                        startActivity(intent)
                    }
                )
            }
        }
    }
}

/**
 * Composable que desenha a interface da Tela 1.
 * @param onNavigateNext Callback disparado quando os dados são validados com sucesso.
 */
@Composable
fun TripDataScreen(onNavigateNext: (String, Int, Double) -> Unit) {
    // Gerenciamento de estado utilizando rememberSaveable.
    // Isso garante que os dados digitados não sejam perdidos caso o usuário rotacione a tela (Requisito RNF05).
    var destination by rememberSaveable { mutableStateOf("") }
    var days by rememberSaveable { mutableStateOf("") }
    var budget by rememberSaveable { mutableStateOf("") }

    // Contexto local utilizado para exibir o Toast de erro
    val context = LocalContext.current

    // Layout básico e cru (Column simples)
    Column {
        // Campos de entrada de texto
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

        // Botão para avançar e validar os dados
        Button(onClick = {
            // Retorna null se o usuário digitou letras em campos numéricos.
            val daysInt = days.toIntOrNull()
            val budgetDouble = budget.toDoubleOrNull()

            // Validação estrita dos dados
            if (destination.isNotBlank() &&
                daysInt != null && daysInt > 0 &&
                budgetDouble != null && budgetDouble > 0
            ) {
                // Se tudo estiver correto, aciona o callback passando os dados tipados
                onNavigateNext(destination, daysInt, budgetDouble)
            } else {
                // Caso contrário, exibe um feedback visual de erro para o usuário
                Toast.makeText(context, "Por favor, preencha todos os campos corretamente.", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text("Avançar")
        }
    }
}