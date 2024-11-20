package com.example.airportfly.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.airportfly.ui.Composable.BottomNavigationItems

@Composable
fun NavigationGraph(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(BottomNavigationItems.FlyInfoScreen.route) {
            FlyInfoScreen()
        }
        composable(BottomNavigationItems.CurrencyScreen.route) {
            CurrencyScreen()
        }
    }
}