package com.example.googletvweather.data

import com.example.googletvweather.model.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherRepository {
    private val weatherApi = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(WeatherApiService::class.java)

    private val rainApi = Retrofit.Builder()
        .baseUrl("https://api.rainviewer.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RainViewerApiService::class.java)

    suspend fun fetchWeatherData(zip: String, apiKey: String): Pair<WeatherResponse, RainViewerResponse> {
        val geo = weatherApi.getCoordinates(zip, apiKey)
        val weather = weatherApi.getCurrentWeather(geo.lat, geo.lon, apiKey = apiKey)
        val radar = rainApi.getRadarFrames()
        return Pair(weather, radar)
    }
}
