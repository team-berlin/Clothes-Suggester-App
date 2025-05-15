package com.berlin.data.mapper

import com.berlin.data.remote.dto.WeatherResponseDto
import com.berlin.domain.model.WeatherData
class WeatherMapperImp: Mapper<WeatherResponseDto, WeatherData> {
    override fun map(from: WeatherResponseDto): WeatherData {
        return WeatherData(
            temperature = from.currentWeather.temperature,
            windSpeed = from.currentWeather.windSpeed
        )
    }
}
