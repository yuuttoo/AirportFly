package com.example.airportfly.ui.Composable

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.airportfly.model.FlightStatus

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