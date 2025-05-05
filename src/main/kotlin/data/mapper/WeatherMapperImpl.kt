package com.berlin.data.mapper

import com.berlin.data.dto.WeatherResponse
import com.berlin.domain.model.WeatherData

class WeatherMapperImpl : WeatherMapper {
    override fun toWeatherData(response: WeatherResponse): WeatherData {
        return WeatherData(
            temperature = response.currentWeather.temperature,
            weatherCode = response.currentWeather.weatherCode,
            windSpeed = response.currentWeather.windSpeed
        )
    }
}