package com.example.airportfly.model

data class FlightInfo(
    val scheduledTime: String,
    val actualTime: String,
    val departureAirPortCode: String?,
    val departureAirPort: String?,
    val arrivalAirPortCode: String?,
    val arrivalAirPort: String?,
    val flightNumber: String,
    //val terminal: String, 高雄只有一航廈，api沒提供航廈
    val gate: String,
    val status: FlightStatus
)

