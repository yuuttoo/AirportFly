package com.example.airportfly

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.airportfly.network.CurrencyRepository
import com.example.airportfly.network.currencyService
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FlightScheduleViewModel(): ViewModel() {
    val flyRepository = AirportFlyRepository(kiaApiService)
    val curRepository = CurrencyRepository(currencyService)

    private val _flightSchedules = MutableLiveData<List<FlightSchedule>>()
    val flightSchedules: LiveData<List<FlightSchedule>> = _flightSchedules

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private var updateJob: Job? = null

//    init {
//        fetchFlightSchedules(1, 1)
//    }


    fun fetchFlightSchedules(airFlyLine: Int, airFlyIO: Int) {
            updateJob?.cancel()//make sure only one Job working
            updateJob = viewModelScope.launch {
                flyRepository.fetchAirFlyData(airFlyLine, airFlyIO)
                    .collect { result ->
                        result.onSuccess { data ->
                            Log.e("Success:", data.toString())
                        }.onFailure { error ->
                            Log.e("Error:", error.toString())
                        }
                    }
            }
        }

    fun stopFetchingSchedules() {
        updateJob?.cancel()
        updateJob = null
    }

    override fun onCleared() {
        super.onCleared()
        stopFetchingSchedules()
    }

    fun getRates() {
        viewModelScope.launch {
            curRepository.getExchangeRates(
                apiKey = BuildConfig.CURRENCY_KEY,
                currencies = "JPY,USD,CNY,EUR,AUD,KRW"
            ).collect { result ->
                result.onSuccess { data ->
                    Log.e("Success:", data.toString())
                }.onFailure {
                    Log.e("Error:", error.toString())
                }
            }
        }
    }
}