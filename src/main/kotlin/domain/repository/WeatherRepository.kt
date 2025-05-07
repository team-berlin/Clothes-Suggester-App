package com.berlin.domain.repository

import com.berlin.domain.model.WeatherData

interface WeatherRepository {
    suspend fun fetchWeather(): WeatherData
}