package com.example.airportfly.ui.Screen.Tab.FlyOutTab

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.airportfly.Utility
import com.example.airportfly.ui.Composable.FlightList
import com.example.airportfly.ui.Screen.FlightScheduleViewModel
import com.example.airportfly.ui.Screen.Tab.FlightScheduleUiState
import kotlinx.coroutines.delay

@Composable
fun FlyInfoOutScreen(
    viewModel: FlightScheduleViewModel,
    isVisible: Boolean
) {
    val flyOutState by viewModel.flyOutScheduleState.collectAsState()

    LaunchedEffect(isVisible) {
        if(isVisible) {
            viewModel.fetchOutboundFlights()

            while(true) {
                delay(Utility.REFRESH_INTERVAL)
                if(isVisible) {
                    viewModel.fetchOutboundFlights()
                }
            }
        }
    }

    when (val state = flyOutState) {
        is FlightScheduleUiState.Loading -> {
            CircularProgressIndicator()
        }
        is FlightScheduleUiState.Success -> {
            FlightList(flights = state.flights)
        }
        is FlightScheduleUiState.Error -> {
            Text(
                text = "載入資料失敗: ${state.message}",
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}


