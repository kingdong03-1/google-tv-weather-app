package com.example.googletvweather.ui

import android.app.Application
import androidx.compose.runtime.*
import androidx.lifecycle.*
import com.example.googletvweather.data.WeatherRepository
import com.example.googletvweather.model.*
import kotlinx.coroutines.launch

class WeatherViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = WeatherRepository()
    
    var zipCode by mutableStateOf("")
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)
    var weatherData by mutableStateOf<WeatherResponse?>(null)
    var radarData by mutableStateOf<RainViewerResponse?>(null)
    var currentFrameIndex by mutableStateOf(0)

    fun updateZip(newZip: String) {
        zipCode = newZip
    }

    fun fetchWeather(apiKey: String) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                val (weather, radar) = repository.fetchWeatherData(zipCode, apiKey)
                weatherData = weather
                radarData = radar
                startRadarLoop()
            } catch (e: Exception) {
                errorMessage = "Failed to fetch weather: ${e.localizedMessage}"
            } finally {
                isLoading = false
            }
        }
    }

    private fun startRadarLoop() {
        viewModelScope.launch {
            while (true) {
                kotlinx.coroutines.delay(500)
                val frames = radarData?.radar?.past ?: emptyList()
                if (frames.isNotEmpty()) {
                    currentFrameIndex = (currentFrameIndex + 1) % frames.size
                }
            }
        }
    }
}
