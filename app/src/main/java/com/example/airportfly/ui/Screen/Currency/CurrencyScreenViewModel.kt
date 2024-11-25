package com.example.airportfly.ui.Screen.Currency

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.airportfly.BuildConfig
import com.example.airportfly.network.CurrencyRepository
import com.example.airportfly.network.currencyService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CurrencyScreenViewModel: ViewModel() {
    private val curRepository = CurrencyRepository(currencyService)

    private val _uiState = MutableStateFlow(CurrencyUiState())
    val uiState: StateFlow<CurrencyUiState> = _uiState.asStateFlow()

    fun getRates() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            curRepository.getExchangeRates(
                apiKey = BuildConfig.CURRENCY_KEY,
                currencies = "JPY,USD,CNY,EUR,AUD,KRW"
            ).collect { result ->
                result.onSuccess { data ->
                    _uiState.update {
                        it.copy(
                            rates = data.rates,
                            isLoading = false,
                            error = null
                        )
                    }
                }.onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = error.message
                        )
                    }
                }
            }
        }
    }

}