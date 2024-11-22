package com.example.airportfly.ui.Screen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.airportfly.AirportFlyRepository
import com.example.airportfly.BuildConfig
import com.example.airportfly.FlightSchedule
import com.example.airportfly.Utility
import com.example.airportfly.kiaApiService
import com.example.airportfly.model.FlightInfo
import com.example.airportfly.model.FlightStatus
import com.example.airportfly.network.CurrencyRepository
import com.example.airportfly.network.currencyService
import com.example.airportfly.ui.Screen.Tab.FlightScheduleUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FlightScheduleViewModel: ViewModel() {
    val flyRepository = AirportFlyRepository(kiaApiService)
    val curRepository = CurrencyRepository(currencyService)

    private val _flyInScheduleState = MutableStateFlow<FlightScheduleUiState>(FlightScheduleUiState.Loading)
    val flyInScheduleState: StateFlow<FlightScheduleUiState> = _flyInScheduleState.asStateFlow()

    private val _flyOutScheduleState = MutableStateFlow<FlightScheduleUiState>(FlightScheduleUiState.Loading)
    val flyOutScheduleState: StateFlow<FlightScheduleUiState> = _flyOutScheduleState.asStateFlow()

    private var inboundJob: Job? = null
    private var outboundJob: Job? = null


    fun fetchOutboundFlights() {
        outboundJob?.cancel()
        outboundJob = viewModelScope.launch {
            _flyInScheduleState.value = FlightScheduleUiState.Loading
            try {
                flyRepository.fetchAirFlyData(airFlyLine = 1, airFlyIO = 1)
                    .collect { result ->
                        result.onSuccess { it ->
                            Log.e("flightSchedule Success:", it.toString())
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
    }

    fun fetchInboundFlights() {
        inboundJob?.cancel()//make sure only one Job working
        inboundJob = viewModelScope.launch {
            _flyInScheduleState.value = FlightScheduleUiState.Loading
            try {
                flyRepository.fetchAirFlyData(airFlyLine = 1, airFlyIO = 2)
                    .collect { result ->
                        result.onSuccess { it ->
                            Log.e("flightSchedule Success:", it.toString())
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
    }


    private fun mapToFlightStatus(airFlyStatus: String): FlightStatus {
        return when (airFlyStatus) {
            "DEPARTED" -> FlightStatus.DEPARTED
            "SCHEDULE_CHANGE" -> FlightStatus.SCHEDULE_CHANGE
            "CANCELED" -> FlightStatus.CANCELED
            else -> FlightStatus.SCHEDULE_CHANGE // 預設值
        }
    }


    override fun onCleared() {
        super.onCleared()
        inboundJob?.cancel()
        outboundJob?.cancel()
    }

    fun getRates() {
        viewModelScope.launch {
            curRepository.getExchangeRates(
                apiKey = BuildConfig.CURRENCY_KEY,
                currencies = "JPY,USD,CNY,EUR,AUD,KRW"
            ).collect { result ->
                result.onSuccess { data ->
                    Log.e("Success:", data.toString())
                }.onFailure { error ->
                    Log.e("Error:", error.toString())
                }
            }
        }
    }
}