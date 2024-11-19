package com.example.airportfly.network

import com.example.airportfly.model.CurrencyResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CurrencyRepository(private val currencyService: CurrencyService) {
    suspend fun getExchangeRates(apiKey: String, currencies: String)
    : Flow<Result<CurrencyResponse>> = flow {
        try {
            val response = currencyService.getExchangeRates(apiKey, currencies)
            emit(Result.success(response))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }

    }
}
