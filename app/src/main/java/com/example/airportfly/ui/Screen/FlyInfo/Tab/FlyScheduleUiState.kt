package com.example.airportfly.ui.Screen.FlyInfo.Tab

import com.example.airportfly.model.FlightInfo

sealed class FlightScheduleUiState {
    object Loading : FlightScheduleUiState()
    data class Success(val flights: List<FlightInfo>) : FlightScheduleUiState()
    data class Error(val message: String) : FlightScheduleUiState()
}
