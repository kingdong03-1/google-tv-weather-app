package com.example.googletvweather.config

import android.content.Context
import java.util.Properties

object Config {
    fun getOpenWeatherApiKey(context: Context): String {
        val props = Properties()
        return try {
            context.assets.open("secrets.properties").use { inputStream ->
                props.load(inputStream)
                props.getProperty("OPENWEATHER_API_KEY", "")
            }
        } catch (e: Exception) {
            ""
        }
    }
}
