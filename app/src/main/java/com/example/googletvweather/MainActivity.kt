package com.example.googletvweather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.googletvweather.ui.WeatherViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFF121212)) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen(viewModel: WeatherViewModel = viewModel()) {
    if (viewModel.weatherData == null) {
        ZipInputScreen(viewModel)
    } else {
        WeatherDisplayScreen(viewModel)
    }
}

@Composable
fun ZipInputScreen(viewModel: WeatherViewModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Enter Zip Code",
            color = Color.White,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 32.dp)
        )
        
        Text(
            text = viewModel.zipCode.ifEmpty { "_____" },
            color = Color.Cyan,
            fontSize = 48.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Numeric Keypad
        val keys = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "C", "0", "Enter")
        
        Column {
            for (row in 0 until 4) {
                Row {
                    for (col in 0 until 3) {
                        val key = keys[row * 3 + col]
                        KeyButton(key) {
                            when (key) {
                                "C" -> viewModel.updateZip(viewModel.zipCode.dropLast(1))
                                "Enter" -> viewModel.fetchWeather("3e7198296308bddb8204f0c43b1f4a8e")
                                else -> if (viewModel.zipCode.length < 5) viewModel.updateZip(viewModel.zipCode + key)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun KeyButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.size(80.dp).padding(4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF333333))
    ) {
        Text(text = text, color = Color.White, fontSize = 20.sp)
    }
}

@Composable
fun WeatherDisplayScreen(viewModel: WeatherViewModel) {
    val radarHost = viewModel.radarData?.host ?: ""
    val currentFramePath = viewModel.radarData?.radar?.past?.getOrNull(viewModel.currentFrameIndex)?.path ?: ""
    val radarUrl = "$radarHost$currentFramePath/512/6/${viewModel.currentLat}/${viewModel.currentLon}/1/1_1.png"

    Box(modifier = Modifier.fillMaxSize()) {
        // Placeholder for Google Map
        Box(
            modifier = Modifier.fillMaxSize().background(Color.DarkGray),
            contentAlignment = Alignment.Center
        ) {
            Text("Google Map Background", color = Color.Gray)
        }

        // Radar Overlay
        AsyncImage(
            model = radarUrl,
            contentDescription = "Weather Radar",
            modifier = Modifier.fillMaxSize()
        )

        // Temperature Overlay
        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(48.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "${viewModel.weatherData?.main?.temp?.let { String.format("%.0f", it) }}°F",
                color = Color.White,
                fontSize = 80.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = viewModel.weatherData?.weather?.getOrNull(0)?.description ?: "",
                color = Color.White,
                fontSize = 24.sp
            )
        }
        
        // Back Button
        Button(
            onClick = { viewModel.weatherData = null },
            modifier = Modifier.align(Alignment.BottomEnd).padding(32.dp)
        ) {
            Text("Change Zip")
        }
    }
}
