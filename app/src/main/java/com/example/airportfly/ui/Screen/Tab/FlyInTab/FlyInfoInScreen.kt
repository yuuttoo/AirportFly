package com.example.airportfly.ui.Screen.Tab.FlyInTab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.airportfly.Utility
import com.example.airportfly.model.FlightInfo
import com.example.airportfly.model.FlightStatus
import com.example.airportfly.ui.Composable.FlightList
import com.example.airportfly.ui.Composable.FlightListItem
import com.example.airportfly.ui.Composable.StatusChip
import com.example.airportfly.ui.Screen.FlightScheduleViewModel
import com.example.airportfly.ui.Screen.Tab.FlightScheduleUiState
import kotlinx.coroutines.delay


@Composable
fun FlyInfoInScreen(
    viewModel: FlightScheduleViewModel,
) {
    val flyInState by viewModel.flyInScheduleState.collectAsState()

//    LaunchedEffect(isVisible) {
//        if(isVisible) {
//            viewModel.fetchInboundFlights()
//
//            while(true) {
//                delay(Utility.REFRESH_INTERVAL)
//                if(isVisible) {
//                    viewModel.fetchInboundFlights()
//                }
//            }
//        }
//    }

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









