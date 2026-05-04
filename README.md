# ✈️ FastTripPlanner

![Kotlin](https://img.shields.io/badge/Kotlin-2.2-blue.svg?logo=kotlin)
![Android API](https://img.shields.io/badge/API-26%2B-brightgreen.svg?logo=android)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-UI-success.svg?logo=android)

O **FastTripPlanner** é um aplicativo Android desenvolvido para facilitar o planejamento financeiro de viagens. Ele integra conceitos fundamentais de desenvolvimento móvel, como múltiplas telas (Activities), passagem de parâmetros via **Intents Explícitas** e gerenciamento de estado de UI utilizando Jetpack Compose.

---

## 🎥 Demonstração

**Link para o vídeo de apresentação:** https://youtube.com/shorts/3xY_2iQRaPg?feature=share

---

## 📱 Telas e Funcionalidades

O fluxo do aplicativo é dividido em três `Activities` distintas:

### 1️⃣ Tela de Dados da Viagem (`TripDataActivity`)
- Coleta as informações base: **Destino**, **Número de Dias** e **Orçamento Diário**.
- **Validação:** Exige que todos os campos sejam preenchidos corretamente e com valores válidos antes de avançar.
- Uso de `KeyboardOptions` para exibir o teclado numérico automaticamente.

### 2️⃣ Tela de Opções da Viagem (`TripOptionsActivity`)
- Permite a personalização da viagem através de opções agrupadas em `Cards`.
- **Hospedagem (RadioButtons):** Econômica, Conforto ou Luxo.
- **Serviços Adicionais (Checkboxes):** Transporte, Alimentação e Passeios.
- Áreas de toque expandidas (Touch Targets) para melhor usabilidade.

### 3️⃣ Tela de Resumo da Viagem (`TripSummaryActivity`)
- Exibe um layout elegante em formato de "Recibo", contendo todas as escolhas feitas pelo usuário.
- Aplica a regra de negócios (detalhada abaixo) para calcular e exibir o **Custo Total** da viagem.
- Permite reiniciar o planejamento limpando a pilha de navegação (Backstack) usando *Intent Flags* (`CLEAR_TOP` e `NEW_TASK`).

---

## ⚙️ Regras de Cálculo (Lógica de Negócio)

O cálculo do custo total é isolado da interface e segue rigorosamente a seguinte regra:

1. **Custo Base:** `Número de Dias` × `Orçamento Diário`
2. **Multiplicador de Hospedagem:**
   - Econômica: `1.0`
   - Conforto: `1.5`
   - Luxo: `2.2`
3. **Custos Extras (Somados ao total):**
   - Transporte: Taxa fixa de `+ R$ 300,00`
   - Alimentação: `+ R$ 50,00` por dia
   - Passeios: `+ R$ 120,00` por dia

**Fórmula Final:** `(Custo Base × Multiplicador) + Custos Extras`

---

## 🛠️ Especificações Técnicas e Restrições

Este projeto foi construído seguindo exigências técnicas estritas:

- **Linguagem:** Kotlin `2.2`
- **Minimum SDK:** `26` (Android 8.0 Oreo)
- **Gradle:** `9.2.1` | **AGP:** `9.0.1`
- **UI Toolkit:** Jetpack Compose (Material Design 3).
- **Gerenciamento de Estado:** Uso de `rememberSaveable` para preservar os dados digitados ao rotacionar a tela.
- **Navegação:** Feita exclusivamente utilizando **Intents Explícitas** (transição entre `Activities`), atendendo ao requisito da disciplina sem utilizar bibliotecas externas de rotas.
- **Sem conexões externas:** Sem banco de dados ou APIs web.

---

## 🚀 Como executar o projeto

1. Clone o repositório:
   ```bash
   git clone https://github.com/BernardoFFilho/FastTripPlanner.git
