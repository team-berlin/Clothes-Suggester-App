package com.berlin.data.repository

import com.berlin.data.dto.WeatherResponse
import com.berlin.data.mapper.WeatherMapper
import com.berlin.domain.model.WeatherData
import com.berlin.domain.repository.WeatherRepository
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.json.Json

class WeatherRepositoryImpl(
    private val client: HttpClient,
    private val mapper: WeatherMapper
) : WeatherRepository {
    override suspend fun fetchWeather(latitude: Double, longitude: Double): WeatherData {

        try {
            val response = client.get("https://api.open-meteo.com/v1/forecast") {
                url {
                    parameters.append("latitude", latitude.toString())
                    parameters.append("longitude", longitude.toString())
                    parameters.append("current_weather", "true")
                }
            }.bodyAsText()
            val weatherResponse = Json.decodeFromString<WeatherResponse>(response)
            return mapper.toWeatherData(weatherResponse)
        } catch (e: Exception) {
            throw Exception("Failed to fetch weather: ${e.message}", e)
        }
    }
}
