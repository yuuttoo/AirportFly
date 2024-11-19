package com.example.airportfly

import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("InstantSchedule.ashx")
    suspend fun fetchInstantSchedule(
        @Query("AirFlyLine") airFlyLine: Int,
        @Query("AirFlyIO") airFlyIO: Int
    ): Response<FlightScheduleResponse>

}

val kiaApiService: ApiService by lazy {
    Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(ApiService::class.java)
}