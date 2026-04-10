package com.example.googletvweather.data

import com.example.googletvweather.model.*
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("geo/1.0/zip")
    suspend fun getCoordinates(
        @Query("zip") zip: String,
        @Query("appid") appid: String
    ): GeocodingResponse

    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String = "imperial",
        @Query("appid") appid: String
    ): WeatherResponse
}
