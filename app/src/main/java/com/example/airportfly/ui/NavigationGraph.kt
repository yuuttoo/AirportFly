package com.example.airportfly.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.airportfly.ui.Composable.BottomNavigationItems
import com.example.airportfly.ui.Screen.Currency.CurrencyScreen
import com.example.airportfly.ui.Screen.Currency.CurrencyScreenViewModel
import com.example.airportfly.ui.Screen.FlyInfo.FlightScheduleViewModel
import com.example.airportfly.ui.Screen.FlyInfo.FlyInfoScreen

@Composable
fun NavigationGraph(
    navController: NavHostController,
    startDestination: String,
    flightScheduleViewModel: FlightScheduleViewModel,
    currencyScreenViewModel: CurrencyScreenViewModel
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(BottomNavigationItems.FlyInfoScreen.route) {
            FlyInfoScreen(flightScheduleViewModel)
        }
        composable(BottomNavigationItems.CurrencyScreen.route) {
            CurrencyScreen(currencyScreenViewModel)
        }
    }
}