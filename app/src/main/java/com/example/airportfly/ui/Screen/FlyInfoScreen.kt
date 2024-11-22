package com.example.airportfly.ui.Screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.example.airportfly.ui.Screen.Tab.FlyInTab.FlyInfoInScreen
import com.example.airportfly.ui.FlyInfoTabItems
import com.example.airportfly.ui.Screen.Tab.FlyOutTab.FlyInfoOutScreen
import kotlinx.coroutines.launch


@Composable
fun FlyInfoScreen(viewModel: FlightScheduleViewModel) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { FlyInfoTabItems.entries.size })
    val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }

    LaunchedEffect(pagerState.currentPage) {
        when (pagerState.currentPage) {
            0 -> viewModel.startPolling(isInbound = false)
            1 -> viewModel.startPolling(isInbound = true)
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.stopPolling()
        }
    }
    Scaffold(
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding())
        ) {
            TabRow(
                selectedTabIndex = selectedTabIndex.value,
                modifier = Modifier.fillMaxWidth()
            ) {
                FlyInfoTabItems.entries.forEachIndexed { index, currentTab ->
                    Tab(
                        selected = selectedTabIndex.value == index,
                        selectedContentColor = MaterialTheme.colorScheme.primary,
                        unselectedContentColor = MaterialTheme.colorScheme.outline,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(currentTab.ordinal)
                            }
                        },
                        text = { Text(text = currentTab.text )},
                        icon = {
                            Icon(
                                imageVector = if (selectedTabIndex.value == index)
                                    ImageVector.vectorResource(currentTab.selectedIcon) else ImageVector.vectorResource(currentTab.unselectedIcon),
                                contentDescription = "Tab Icon"
                            )
                        }
                    )
                }
            }
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) { page ->
                when (page) {
                    0 -> FlyInfoOutScreen(viewModel)
                    1 -> FlyInfoInScreen(viewModel)
                }
            }
        }
    }
}

