package com.example.airportfly.ui

import android.icu.text.UnicodeSet.SpanCondition
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
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.airportfly.model.FlightInfo
import com.example.airportfly.model.FlightStatus


@Composable
fun FlyInfoInScreen() {
    val sampleFlights = listOf(
        FlightInfo(
            scheduledTime = "14:30",
            actualTime = "14:35",
            departure = "TAE大邱機場",
            arrival = "TPE台北桃園國際機場",
            flightNumber = "786",
            terminal = "01",
            gate = "A9",
            status = FlightStatus.DEPARTED
        ),
        FlightInfo(
            scheduledTime = "14:30",
            actualTime = "14:35",
            departure = "TAE大邱機場",
            arrival = "TPE台北桃園國際機場",
            flightNumber = "786",
            terminal = "01",
            gate = "A9",
            status = FlightStatus.DEPARTED
        ),
    )

    FlightList(flights = sampleFlights)
}

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

@Composable
fun FlightListItem(flight: FlightInfo) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "預計: ${flight.scheduledTime}",
                        fontSize = 16.sp
                    )
                    Text(
                        text = "實際: ${flight.actualTime}",
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                StatusChip(status = flight.status)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 航線資訊
            Text(
                text = "${flight.departure} - ${flight.arrival}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 航班和登機門資訊
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "航班號碼: ${flight.flightNumber}",
                    fontSize = 16.sp
                )
                Text(
                    text = "航廈/登機門: ${flight.terminal}/${flight.gate}",
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun StatusChip(status: FlightStatus) {
    val (backgroundColor, textColor, text) = when (status) {
        FlightStatus.DEPARTED -> Triple(
            Color(0xFF4CAF50),
            Color.White,
            "出發 DEPARTED"
        )
        FlightStatus.SCHEDULE_CHANGE -> Triple(
            Color(0xFFFFA000),
            Color.White,
            "時間更改 SCHEDULE CHANGE"
        )
        FlightStatus.CANCELED -> Triple(
            Color(0xFFE53935),
            Color.White,
            "取消 CANCELED"
        )
    }

    Surface(
        color = backgroundColor,
        shape = MaterialTheme.shapes.small
    ) {
        Text(
            text = text,
            color = textColor,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            fontSize = 14.sp
        )
    }
}



