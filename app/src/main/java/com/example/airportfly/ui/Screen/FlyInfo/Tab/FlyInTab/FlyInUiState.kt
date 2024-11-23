package com.example.airportfly.ui.Screen.FlyInfo.Tab.FlyInTab

import com.example.airportfly.model.FlightStatus

data class FlyInUiState (
    val scheduledTime: String,
    val actualTime: String,
    val departure: String,
    val arrival: String,
    val flightNumber: String,
    val terminal: String,
    val gate: String,
    val status: String
)


