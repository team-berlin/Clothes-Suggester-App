package com.berlin.data.repository

import com.berlin.data.mapper.WeatherMapperImpl
import com.berlin.shered.Mapper
import com.berlin.data.remote.dto.WeatherResponseDto
import com.berlin.domain.exepction.WeatherFetchException
import com.berlin.domain.model.WeatherData
import com.berlin.data.service.CoordinateApi
import com.berlin.domain.repository.WeatherRepository
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.request.*

class WeatherRepositoryImpl(
    private val client: HttpClient,
    private val weatherMapper: WeatherMapperImpl,
    private val coordinateApi: CoordinateApi,
) : WeatherRepository {

    override suspend fun fetchWeather(): WeatherData {
        val coordinates = coordinateApi.fetchCoordinates()

        try {
            val response: WeatherResponseDto =
                client.get("https://api.open-meteo.com/v1/forecast") {
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
}