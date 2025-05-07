package com.berlin.data.repository

import com.berlin.data.dto.WeatherResponse
import com.berlin.data.mapper.IpGeolocationMapper
import com.berlin.data.mapper.WeatherMapper
import com.berlin.domain.exepction.GeolocationFetchException
import com.berlin.domain.exepction.WeatherFetchException
import com.berlin.domain.model.Coordinates
import com.berlin.domain.model.WeatherData
import com.berlin.domain.repository.WeatherRepository
import data.dto.IpGeolocationResponse
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.json.Json

class WeatherRepositoryImpl(
    private val client: HttpClient,
    private val weatherMapper: WeatherMapper,
    private val geolocationMapper: IpGeolocationMapper,
) : WeatherRepository {

    override suspend fun fetchWeather(): WeatherData {
        val coordinates = fetchCoordinates()
        try {
            val response = client.get("https://api.open-meteo.com/v1/forecast") {
                url {
                    parameters.append("latitude", coordinates.latitude.toString())
                    parameters.append("longitude", coordinates.longitude.toString())
                    parameters.append("current_weather", "true")
                }
            }.bodyAsText()

            val weatherResponse = Json.decodeFromString<WeatherResponse>(response)
            return weatherMapper.toWeatherData(weatherResponse)

        } catch (e: Exception) {
            throw WeatherFetchException("Failed to fetch weather")
        }
    }


    private suspend fun fetchCoordinates(): Coordinates {
        try {
            val response = client.get("http://ip-api.com/json").bodyAsText()
            val geolocationResponse = Json.decodeFromString<IpGeolocationResponse>(response)
            return geolocationMapper.toCoordinates(geolocationResponse)
        } catch (e: Exception) {
            throw GeolocationFetchException("Failed to fetch coordinates")
        }
    }
}