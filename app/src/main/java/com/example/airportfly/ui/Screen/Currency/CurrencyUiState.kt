package com.example.airportfly.ui.Screen.Currency

data class CurrencyUiState(
    val rates: Map<String, Double> = emptyMap(),
    val isLoading: Boolean = false,
    val error: String? = null
)
