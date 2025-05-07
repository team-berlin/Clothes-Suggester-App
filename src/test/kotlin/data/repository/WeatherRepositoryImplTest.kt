package com.berlin.data.repository

import com.berlin.data.mapper.IpGeolocationMapperImpl
import com.berlin.data.mapper.WeatherMapperImpl
import com.berlin.domain.exepction.GeolocationFetchException
import com.berlin.domain.exepction.WeatherFetchException
import com.google.common.truth.Truth.assertThat
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class WeatherRepositoryImplTest {

    private lateinit var repository: WeatherRepositoryImpl
    private lateinit var client: HttpClient

    private val ipApiResponseJson = """{
    "status": "success",
    "country": "Germany",
    "countryCode": "DE",
    "region": "BE",
    "regionName": "Berlin",
    "city": "Berlin",
    "zip": "10115",
    "lat": 52.52,
    "lon": 13.405,
    "timezone": "Europe/Berlin",
    "isp": "Test ISP",
    "org": "Test Org",
    "as": "AS123 Test",
    "query": "127.0.0.1"
}"""


    private val weatherApiResponseJson = """{
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

    @BeforeEach
    fun setup() {
        val mockEngine = MockEngine { request ->
            when (request.url.toString()) {
                "http://ip-api.com/json" -> respond(
                    ipApiResponseJson,
                    HttpStatusCode.OK,
                    headersOf("Content-Type", "application/json")
                )
                "https://api.open-meteo.com/v1/forecast?latitude=52.52&longitude=13.405&current_weather=true" -> respond(
                    weatherApiResponseJson,
                    HttpStatusCode.OK,
                    headersOf("Content-Type", "application/json")
                )
                else -> respondError(HttpStatusCode.NotFound)
            }
        }

        client = HttpClient(mockEngine)
        repository = WeatherRepositoryImpl(client, WeatherMapperImpl(), IpGeolocationMapperImpl())
    }

    @Test
    fun `fetchWeather should return mapped WeatherData`() = runBlocking {
        val result = repository.fetchWeather()

        assertThat(result.temperature).isEqualTo(18.5)
        assertThat(result.windSpeed).isEqualTo(5.2)
    }

    @Test
    fun `fetchWeather should throw GeolocationFetchException when geolocation fails`() = runBlocking {
        val engine = MockEngine { respondError(HttpStatusCode.BadRequest) }
        val client = HttpClient(engine)
        val repository = WeatherRepositoryImpl(client, WeatherMapperImpl(), IpGeolocationMapperImpl())

        val exception = assertThrows<GeolocationFetchException> {
            repository.fetchWeather()
        }

        assertThat(exception.message).isEqualTo("Failed to fetch coordinates") // <- updated message
    }


    @Test
    fun `fetchWeather should throw WeatherFetchException when weather fetch fails`() = runBlocking {
        val engine = MockEngine { request ->
            when (request.url.toString()) {
                "http://ip-api.com/json" -> respond(
                    ipApiResponseJson,
                    HttpStatusCode.OK,
                    headersOf("Content-Type", "application/json")
                )
                else -> respondError(HttpStatusCode.BadRequest)
            }
        }

        val client = HttpClient(engine)
        val repository = WeatherRepositoryImpl(client, WeatherMapperImpl(), IpGeolocationMapperImpl())

        val exception = assertThrows<WeatherFetchException> {
            repository.fetchWeather()
        }

        assertThat(exception.message).isEqualTo("Failed to fetch weather")
    }
}
