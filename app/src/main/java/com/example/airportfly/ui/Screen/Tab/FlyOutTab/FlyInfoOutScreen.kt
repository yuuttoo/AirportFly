package com.example.airportfly.ui.Screen.Tab.FlyOutTab

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.airportfly.ui.Composable.FlightList
import com.example.airportfly.ui.Screen.FlightScheduleViewModel
import com.example.airportfly.ui.Screen.Tab.FlightScheduleUiState

@Composable
fun FlyInfoOutScreen(viewModel: FlightScheduleViewModel) {
    val flyOutState by viewModel.flyOutScheduleState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
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
}


