package com.berlin.data.repository

import com.berlin.data.mapper.Mapper
import com.berlin.data.remote.dto.IpGeolocationResponseDto
import com.berlin.data.remote.dto.WeatherResponseDto
import com.berlin.domain.exepction.GeolocationFetchException
import com.berlin.domain.exepction.WeatherFetchException
import com.berlin.domain.model.Coordinates
import com.berlin.domain.model.WeatherData
import com.berlin.domain.repository.WeatherRepository
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.json.Json

class WeatherRepositoryImpl(
    private val client: HttpClient,
    private val weatherMapper: Mapper<WeatherResponseDto, WeatherData>,
    private val geolocationMapper: Mapper<IpGeolocationResponseDto, Coordinates>,
) : WeatherRepository {

    override suspend fun fetchWeather(): WeatherData {
        val coordinates = fetchCoordinates()
        try {
            val response: WeatherResponseDto = client.get("https://api.open-meteo.com/v1/forecast") {
                url {
                    parameters.append("latitude", coordinates.latitude.toString())
                    parameters.append("longitude", coordinates.longitude.toString())
                    parameters.append("current_weather", "true")
                }
            }.body()

            return weatherMapper.map(response)

        } catch (e: Exception) {
            throw WeatherFetchException("Failed to fetch weather")
        }
    }


    private suspend fun fetchCoordinates(): Coordinates {
        try {
            val response: IpGeolocationResponseDto = client.get("http://ip-api.com/json").body()
            return geolocationMapper.map(response)
        } catch (e: Exception) {
            throw GeolocationFetchException("Failed to fetch coordinates")
        }
    }
}