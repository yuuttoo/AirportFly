package com.example.airportfly.ui.Composable

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import com.example.airportfly.model.FlightInfo

@Composable
fun FlightList(flights: List<FlightInfo>) {
    LazyColumn {
        flights.forEach { flight ->
            item {
                FlightListItem(flight)
            }
        }
    }
}