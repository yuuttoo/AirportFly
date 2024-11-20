package com.example.airportfly.ui.Composable

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.example.airportfly.R

sealed class BottomNavigationItems(
    val route: String,
    val title: String,
    val icon: Int
) {
    object FlyInfoScreen : BottomNavigationItems (
        route = "FlyInfoScreen",
        title = "航班",
        icon = R.drawable.ic_airport
    )
    object CurrencyScreen : BottomNavigationItems (
        route = "CurrencyScreen",
        title = "匯率",
        icon = R.drawable.ic_money

    )
}