package com.example.airportfly

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FlightScheduleResponse (
    @Json(name = "InstantSchedule")
    val instantSchedule: List<FlightSchedule>
)

@JsonClass(generateAdapter = true)
data class FlightSchedule(
    @Json(name = "expectTime") val expectTime: String,
    @Json(name = "realTime") val realTime: String,
    @Json(name = "airLineName") val airLineName: String,
    @Json(name = "airLineCode") val airLineCode: String,
    @Json(name = "airLineLogo") val airLineLogo: String,
    @Json(name = "airLineUrl") val airLineUrl: String,
    @Json(name = "airLineNum") val airLineNum: String,
    @Json(name = "goalAirportCode") val goalAirportCode: String,
    @Json(name = "goalAirportName") val goalAirportName: String,
    @Json(name = "airPlaneType") val airPlaneType: String,
    @Json(name = "airBoardingGate") val airBoardingGate: String,
    @Json(name = "airFlyStatus") val airFlyStatus: String,
    @Json(name = "airFlyDelayCause") val airFlyDelayCause: String
)
