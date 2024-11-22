package com.example.airportfly.ui.Composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.airportfly.model.FlightInfo

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
                text = "${flight.departureAirPortCode} ${flight.departureAirPort} - ${flight.arrivalAirPortCode} ${flight.arrivalAirPort}",
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
                    text = "登機門: ${flight.gate}",
                    fontSize = 16.sp
                )
            }
        }
    }
}