package br.edu.ifsp.scl.sc3037291.fasttripplanner

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
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
                // Surface: Fornece o fundo padrão e cor do tema MD3
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
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
}

/**
 * Composable que desenha a interface da Tela 1 com Material Design 3.
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

    // Column: Organiza os elementos verticalmente com espaçamento padronizado de 16dp
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título estilizado para melhorar a hierarquia visual
        Text(
            text = "Planeje sua Viagem",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Card: Agrupa o formulário com elevação e bordas arredondadas para melhor UX
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // OutlinedTextField: Componente MD3 com contorno e label flutuante
                OutlinedTextField(
                    value = destination,
                    onValueChange = { destination = it },
                    label = { Text("Destino") },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Ex: Paris, França") },
                    // KeyboardOptions.Words: Capitaliza a primeira letra de cada palavra digitada
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words
                    ),
                    singleLine = true
                )

                OutlinedTextField(
                    value = days,
                    onValueChange = { days = it },
                    label = { Text("Quantidade de Dias") },
                    modifier = Modifier.fillMaxWidth(),
                    // KeyboardType.Number: Abre o teclado numérico para facilitar a entrada do usuário
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    singleLine = true
                )

                OutlinedTextField(
                    value = budget,
                    onValueChange = { budget = it },
                    label = { Text("Orçamento Diário (R$)") },
                    modifier = Modifier.fillMaxWidth(),
                    // KeyboardType.Decimal: Abre o teclado com ponto/vírgula para valores monetários
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal
                    ),
                    singleLine = true
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Botão para avançar e validar os dados, estilizado com ícone e largura total
        Button(
            onClick = {
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
                    Toast.makeText(
                        context,
                        "Por favor, preencha todos os campos corretamente.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            // Row interna do botão para alinhar Texto e Ícone horizontalmente
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text("Avançar", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}
