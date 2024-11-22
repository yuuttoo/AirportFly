package com.example.airportfly.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.airportfly.ui.Composable.BottomNavigationItems
import com.example.airportfly.ui.Screen.FlightScheduleViewModel
import com.example.airportfly.ui.Screen.FlyInfoScreen

@Composable
fun NavigationGraph(
    navController: NavHostController,
    startDestination: String,
    viewModel: FlightScheduleViewModel
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(BottomNavigationItems.FlyInfoScreen.route) {
            FlyInfoScreen(viewModel)
        }
        composable(BottomNavigationItems.CurrencyScreen.route) {
            CurrencyScreen()
        }
    }
}