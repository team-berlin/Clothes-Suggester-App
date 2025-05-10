package com.berlin.data.mapper

import com.berlin.data.remote.dto.WeatherResponseDto
import com.berlin.domain.model.WeatherData

class WeatherMapperImpl : WeatherMapper {
    override fun toWeatherData(response: WeatherResponseDto): WeatherData {
        return WeatherData(
            temperature = response.currentWeather.temperature,
            windSpeed = response.currentWeather.windSpeed
        )
    }
}