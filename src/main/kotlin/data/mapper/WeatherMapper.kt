package com.berlin.data.mapper

import com.berlin.data.remote.dto.WeatherResponseDto
import com.berlin.domain.model.WeatherData

interface WeatherMapper {
    fun toWeatherData(response: WeatherResponseDto): WeatherData
}