package com.example.airportfly.ui

sealed class Routes(val route: String) {
    object FlyInfo : Routes("FlyInfoScreen")
    object Currency : Routes("CurrencyScreen")

}