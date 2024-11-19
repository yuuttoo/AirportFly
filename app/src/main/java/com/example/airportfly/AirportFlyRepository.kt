package com.example.airportfly

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class AirportFlyRepository(private val apiService: ApiService) {
    suspend fun  fetchAirFlyData(airFlyLine: Int, airFlyIO: Int)
    : Flow<Result<FlightScheduleResponse>> = flow {
        try {
            val response = apiService.fetchInstantSchedule(airFlyLine, airFlyIO)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Result.success(it))
                } ?: throw Exception("Response body is null")
            } else {
                throw Exception("Error: ${response.code()}: ${response.message()}")
            }
        } catch (e: Exception) {
            throw e
        }
    }.catch { e ->
        emit(Result.failure(e))
    }
}