package com.example.googletvweather.model

import com.google.gson.annotations.SerializedName

data class GeocodingResponse(
    val name: String,
    val lat: Double,
    val lon: Double,
    val country: String
)

data class WeatherResponse(
    val main: MainData,
    val weather: List<WeatherDescription>,
    val name: String
)

data class MainData(
    val temp: Double
)

data class WeatherDescription(
    val description: String
)

data class RainViewerResponse(
    val host: String,
    val radar: RadarData
)

data class RadarData(
    val past: List<RadarFrame>
)

data class RadarFrame(
    val time: Long,
    val path: String
)
