package com.example.airportfly.ui

import com.example.airportfly.R

enum class FlyInfoTabItems (
    val text: String,
    val selectedIcon: Int,
    val unselectedIcon: Int,
) {
    FlyInfoOutTab (
        text = "起飛班機",
        selectedIcon = R.drawable.ic_flight_takeoff,
        unselectedIcon = R.drawable.ic_flight_takeoff
    ),
    FlyInfoInTab (
    text = "抵達班機",
    selectedIcon = R.drawable.ic_flight_land,
    unselectedIcon = R.drawable.ic_flight_land
    )
}
