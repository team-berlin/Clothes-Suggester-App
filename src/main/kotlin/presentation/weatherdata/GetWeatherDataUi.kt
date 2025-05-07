package com.berlin.presentation.weatherdata

import com.berlin.domain.usecase.GetWeatherUseCase
import com.berlin.presentation.UiRunner
import com.berlin.presentation.io.Reader
import com.berlin.presentation.io.Viewer

class GetWeatherDataUi(
    private val viewer: Viewer,
    private val getWeatherUseCase: GetWeatherUseCase,
) : UiRunner {
    override val id: Int = 1
    override val label: String = "view weather report"

    override suspend fun start() {
        try {
            val weatherData = getWeatherUseCase()
            viewer.show("Weather: ${weatherData.temperature}°C")
        } catch (e: Exception) {
            viewer.show("Failed to fetch weather: ${e.message}")
        }
    }
}


