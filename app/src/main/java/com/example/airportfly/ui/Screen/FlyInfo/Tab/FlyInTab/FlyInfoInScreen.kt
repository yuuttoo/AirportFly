package com.example.airportfly.ui.Screen.FlyInfo.Tab.FlyInTab

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
import com.example.airportfly.ui.Screen.FlyInfo.FlightScheduleViewModel
import com.example.airportfly.ui.Screen.FlyInfo.Tab.FlightScheduleUiState


@Composable
fun FlyInfoInScreen(
    viewModel: FlightScheduleViewModel,
) {
    val flyInState by viewModel.flyInScheduleState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (val state = flyInState) {
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









