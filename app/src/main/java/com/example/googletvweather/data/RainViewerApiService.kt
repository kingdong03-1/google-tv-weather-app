package com.example.googletvweather.data

import com.example.googletvweather.model.RainViewerResponse
import retrofit2.http.GET

interface RainViewerApiService {
    @GET("public/weather-maps.json")
    suspend fun getRadarFrames(): RainViewerResponse
}
