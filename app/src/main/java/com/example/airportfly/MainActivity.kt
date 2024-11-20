package com.example.airportfly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.airportfly.ui.Composable.BottomNavigationItems
import com.example.airportfly.ui.NavigationGraph
import com.example.airportfly.ui.theme.AirportFlyTheme
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : ComponentActivity() {
    val vm = FlightScheduleViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AirportFlyTheme {
                val navController: NavHostController = rememberNavController()

                Scaffold(
                    bottomBar = {
                        BottomBar(
                            navController = navController,
                            modifier = Modifier
                        )
                    }) { innerPadding ->
                    Box(
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        NavigationGraph(
                            navController = navController,
                            startDestination = BottomNavigationItems.FlyInfoScreen.route
                        )
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        vm.fetchFlightSchedules(1, 1)
        //vm.getRates()//匯率資料
    }

    override fun onStop() {
        super.onStop()
        vm.stopFetchingSchedules()
    }
}

@Composable
fun BottomBar(
    navController: NavController,
    modifier: Modifier
) {
    val screens = listOf(
        BottomNavigationItems.FlyInfoScreen,
        BottomNavigationItems.CurrencyScreen
    )

    NavigationBar(
        modifier = modifier,
        containerColor =  Color.LightGray
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        screens.forEach { screen ->
            NavigationBarItem(
                label = {
                    Text(text = screen.title)
                },
                icon = {
                    Icon(imageVector = ImageVector.vectorResource(screen.icon), contentDescription = "")
                },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    unselectedTextColor = Color.Gray,
                    selectedTextColor = Color.Black,
                    selectedIconColor = Color.Black,
                    unselectedIconColor = Color.Black,
                    indicatorColor = Color.White
                ),
            )
        }
    }
}
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AirportFlyTheme {
        Greeting("Android")
    }
}