package com.example.airportfly.ui.Screen.FlyInfo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.airportfly.AirportFlyRepository
import com.example.airportfly.BuildConfig
import com.example.airportfly.Utility
import com.example.airportfly.kiaApiService
import com.example.airportfly.model.FlightInfo
import com.example.airportfly.model.FlightStatus
import com.example.airportfly.network.CurrencyRepository
import com.example.airportfly.network.currencyService
import com.example.airportfly.ui.Screen.FlyInfo.Tab.FlightScheduleUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FlightScheduleViewModel: ViewModel() {
    val flyRepository = AirportFlyRepository(kiaApiService)

    private val _flyInScheduleState = MutableStateFlow<FlightScheduleUiState>(FlightScheduleUiState.Loading)
    val flyInScheduleState: StateFlow<FlightScheduleUiState> = _flyInScheduleState.asStateFlow()

    private val _flyOutScheduleState = MutableStateFlow<FlightScheduleUiState>(FlightScheduleUiState.Loading)
    val flyOutScheduleState: StateFlow<FlightScheduleUiState> = _flyOutScheduleState.asStateFlow()


    private var pollingJob: Job? = null

    fun startPolling(isInbound: Boolean) {
        stopPolling()//make sure only 1 job working

        pollingJob = viewModelScope.launch {
            while(true) {
                if(isInbound) {
                    fetchInboundFlights()
                } else {
                    fetchOutboundFlights()
                }
                delay(Utility.REFRESH_INTERVAL)
            }
        }
    }

    fun stopPolling() {
        pollingJob?.cancel()
        pollingJob = null
    }


    private suspend fun fetchOutboundFlights() {
            _flyInScheduleState.value = FlightScheduleUiState.Loading
            try {
                flyRepository.fetchAirFlyData(airFlyLine = 1, airFlyIO = 1)
                    .collect { result ->
                        result.onSuccess {
                            _flyOutScheduleState.value = FlightScheduleUiState.Success(
                                it.instantSchedule.map { flightSchedule ->
                                    FlightInfo(
                                        scheduledTime = flightSchedule.expectTime,
                                        actualTime = flightSchedule.realTime,
                                        departureAirPortCode = Utility.BASE_AIRPORT_CODE,
                                        departureAirPort = Utility.BASE_AIRPORT,
                                        arrivalAirPortCode = flightSchedule.goalAirportCode,
                                        arrivalAirPort = flightSchedule.goalAirportName,
                                        flightNumber = flightSchedule.airLineNum,
                                        gate = flightSchedule.airBoardingGate,
                                        status = mapToFlightStatus(flightSchedule.airFlyStatus)
                                    )
                                }
                            )
                        }.onFailure { error ->
                            _flyOutScheduleState.value = FlightScheduleUiState.Error(
                                error.message ?: "無法載入航班資料"
                            )
                        }
                    }
            } catch (e: Exception) {
                _flyOutScheduleState.value = FlightScheduleUiState.Error(
                    e.message ?: "網路連線錯誤"
                )
            }
    }

    suspend fun fetchInboundFlights() {
            _flyInScheduleState.value = FlightScheduleUiState.Loading
            try {
                flyRepository.fetchAirFlyData(airFlyLine = 1, airFlyIO = 2)
                    .collect { result ->
                        result.onSuccess {
                            //Log.e("flightSchedule Success:", it.toString())
                            _flyInScheduleState.value = FlightScheduleUiState.Success(
                                it.instantSchedule.map { flightSchedule ->
                                    FlightInfo(
                                        scheduledTime = flightSchedule.expectTime,
                                        actualTime = flightSchedule.realTime,
                                        departureAirPortCode = flightSchedule.upAirportCode,
                                        departureAirPort = flightSchedule.upAirportName,
                                        arrivalAirPortCode = Utility.BASE_AIRPORT_CODE,
                                        arrivalAirPort = Utility.BASE_AIRPORT,
                                        flightNumber = flightSchedule.airLineNum,
                                        gate = flightSchedule.airBoardingGate,
                                        status = mapToFlightStatus(flightSchedule.airFlyStatus)
                                    )
                                }
                            )
                        }.onFailure { error ->
                            _flyInScheduleState.value = FlightScheduleUiState.Error(
                                error.message ?: "無法載入航班資料"
                            )
                        }
                    }
            } catch (e: Exception) {
                _flyInScheduleState.value = FlightScheduleUiState.Error(
                    e.message ?: "網路連線錯誤"
                )
            }
    }


    private fun mapToFlightStatus(airFlyStatus: String): FlightStatus {
        return when (airFlyStatus) {
            "離站Departed" -> FlightStatus.DEPARTED
            "準時On Time" -> FlightStatus.ON_TIME
            "抵達Arrived" -> FlightStatus.ARRIVED
            "延遲Delayed" -> FlightStatus.DELAYED
            "取消Cancelled" -> FlightStatus.CANCELED
            "SCHEDULE_CHANGE" -> FlightStatus.SCHEDULE_CHANGE
            else -> FlightStatus.SCHEDULE_CHANGE // 預設值
        }
    }

    override fun onCleared() {
        super.onCleared()
        stopPolling()
    }


}