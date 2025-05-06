package com.berlin.data.repository

import com.berlin.data.mapper.WeatherMapperImpl
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondError
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class WeatherRepositoryImplTest {

    private lateinit var repository: WeatherRepositoryImpl
    private lateinit var client: HttpClient

    @BeforeEach
    fun setup() {
        val mockEngine = MockEngine { request ->
            respond(
                jsonResponse, HttpStatusCode.OK,
                headersOf("Content-Type", "application/json")
            )
        }

        client = HttpClient(mockEngine)
        val mapper = WeatherMapperImpl()
        repository = WeatherRepositoryImpl(client, mapper)
    }

    private val jsonResponse = """{
        "latitude": 52.52,
        "longitude": 13.405,
        "generationtime_ms": 168.0,
        "utc_offset_seconds": 3600,
        "timezone": "Europe/Berlin",
        "timezone_abbreviation": "CET",
        "elevation": 35.0,
        "current_weather_units": {
            "time": "ISO 8601 formatted string",
            "interval": "minutes",
            "temperature": "degrees Celsius",
            "windspeed": "meters per second",
            "winddirection": "degrees",
            "is_day": "0 for night, 1 for day",
            "weathercode": "weather condition code"
        },
        "current_weather": {
            "time": "2025-05-06T12:00:00Z",
            "interval": 60,
            "temperature": 18.5,
            "windspeed": 5.2,
            "winddirection": 180,
            "is_day": 1,
            "weathercode": 800
        }
    }"""

    @Test
    fun `fetchWeather should return mapped WeatherData`() = runBlocking {
        val result = repository.fetchWeather(52.52, 13.405)

        assertEquals(18.5, result.temperature)
        assertEquals(5.2, result.windSpeed)
    }

    @Test
    fun `fetchWeather should throw an exception when API request fails`() = runBlocking {
        val failingEngine = MockEngine { respondError(HttpStatusCode.BadRequest) }
        val failingClient = HttpClient(failingEngine)
        val failingRepository = WeatherRepositoryImpl(failingClient, WeatherMapperImpl())

        val exception = assertThrows<Exception> {
            failingRepository.fetchWeather(52.52, 13.405)
        }

        assertEquals("Failed to fetch weather", exception.message)
    }
}
