package com.berlin.domain.usecase

import com.berlin.domain.model.WeatherData
import com.berlin.domain.repository.WeatherRepository

class GetWeatherUseCase(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(latitude: Double, longitude: Double): WeatherData {

        return repository.fetchWeather(latitude, longitude)
    }
}