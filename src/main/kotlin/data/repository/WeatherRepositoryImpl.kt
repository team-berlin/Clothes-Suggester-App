package com.berlin.data.repository

import com.berlin.data.mapper.WeatherMapper
import com.berlin.domain.model.WeatherData
import com.berlin.domain.repository.WeatherRepository
import io.ktor.client.*

class WeatherRepositoryImpl(
    private val client: HttpClient,
    private val mapper: WeatherMapper
) : WeatherRepository {
    override suspend fun fetchWeather(latitude: Double, longitude: Double): WeatherData {
        TODO()
    }
}