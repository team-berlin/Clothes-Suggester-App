package com.berlin.presentation.weatherdata

import com.berlin.domain.usecase.GetWeatherUseCase
import com.berlin.presentation.UiRunner
import com.berlin.presentation.io.Reader
import com.berlin.presentation.io.Viewer

class SuggestClothesTemperatureUi(
    private val viewer: Viewer,
    private val reader: Reader,
    private val getWeatherUseCase: GetWeatherUseCase,
) : UiRunner {
    override val id: Int = 1
    override val label: String = "view weather report"

    override suspend fun start() {
        val latitude = getLatitude() ?: return

        val longitude = getLongitude() ?: return

        try {
            val weatherData = getWeatherUseCase(latitude, longitude)
            viewer.show("Weather: ${weatherData.temperature}°C")
        } catch (e: Exception) {
            viewer.show("Failed to fetch weather: ${e.message}")
        }
    }

    fun getLatitude(): Double? {
        viewer.show("Enter latitude (e.g., 32.61889) or type 'back' to return main menu :")
        val latitude = reader.readLatitude()
        if (latitude == null) {
            viewer.show("Invalid latitude, please enter a number.")
        }
        return latitude
    }

    fun getLongitude(): Double? {
        viewer.show("Enter longitude (e.g., 35.79011) or type 'back' to return main menu :")
        val longitude = reader.readLongitude()
        if (longitude == null) {
            viewer.show("Invalid longitude, please enter a number.")
        }
        return longitude
    }
}


