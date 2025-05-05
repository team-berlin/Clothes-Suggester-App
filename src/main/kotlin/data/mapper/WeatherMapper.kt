package com.berlin.data.mapper

import com.berlin.data.dto.WeatherResponse
import com.berlin.domain.model.WeatherData

interface WeatherMapper {
    fun toWeatherData(response: WeatherResponse): WeatherData
}