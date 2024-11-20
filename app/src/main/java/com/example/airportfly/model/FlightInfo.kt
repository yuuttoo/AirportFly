package com.example.airportfly.model



data class FlightInfo(
    val scheduledTime: String,
    val actualTime: String,
    val departure: String,
    val arrival: String,
    val flightNumber: String,
    val terminal: String,
    val gate: String,
    val status: FlightStatus
)

