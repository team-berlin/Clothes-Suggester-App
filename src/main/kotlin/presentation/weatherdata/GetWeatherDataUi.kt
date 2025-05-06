package com.berlin.presentation.weatherdata

import com.berlin.domain.usecase.GetWeatherUseCase
import com.berlin.presentation.UiRunner
import com.berlin.presentation.io.Reader
import com.berlin.presentation.io.Viewer

class GetWeatherDataUi(
    private val viewer: Viewer,
    private val reader: Reader,
    private val getWeatherUseCase: GetWeatherUseCase
) : UiRunner {
    override val id: Int = 1
    override val label: String = "view weather report"

    override suspend fun start() {
        val latitude = getLatitude() ?: return

        val longitude = getLongitude() ?: return

        try {
            val weatherData = getWeatherUseCase(latitude, longitude)
            viewer.show("Weather: ${weatherData.temperature}°C, Code: ${weatherData.weatherCode}")
        } catch (e: Exception) {
            viewer.show("Failed to fetch weather: ${e.message}")
        }
    }

    private fun getLatitude(): Double? {
        viewer.show("Enter latitude (e.g., 32.61889) or type 'back' to return main menu :")
        val latInput = reader.read()?.trim() ?: return null
        if (latInput.equals("back", ignoreCase = true)) return null

        val latitude = latInput.toDoubleOrNull()
        if (latitude != null) {
            return latitude
        }
        viewer.show("Invalid latitude, please enter a number.")
        return null
    }
        private fun getLongitude(): Double?{
            viewer.show("Enter longitude (e.g., 35.79011) or type 'back' to return main menu :")
            val lonInput = reader.read()?.trim() ?: return null
            if (lonInput.equals("back", ignoreCase = true)) return null

            val longitude = lonInput.toDoubleOrNull()
            if (longitude != null) {
                return longitude
            }
                viewer.show("Invalid longitude, please enter a number.")
                return null
            }

        }


