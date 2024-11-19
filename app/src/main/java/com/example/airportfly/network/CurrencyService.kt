package com.example.airportfly.network

import com.example.airportfly.ApiService
import com.example.airportfly.BuildConfig
import com.example.airportfly.model.CurrencyResponse
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyService {
    @GET("latest")
    suspend fun getExchangeRates(
        @Query("apikey") apiKey: String,
        @Query("currencies") currencies: String
    ): CurrencyResponse
}


val currencyService: CurrencyService by lazy {
    Retrofit.Builder()
        .baseUrl(BuildConfig.CURRENCY_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(CurrencyService::class.java)
}