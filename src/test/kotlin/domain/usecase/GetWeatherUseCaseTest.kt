package domain.usecase

import com.berlin.domain.model.WeatherData
import com.berlin.domain.repository.WeatherRepository
import com.berlin.domain.usecase.GetWeatherUseCase
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetWeatherUseCaseTest {

    private lateinit var repository: WeatherRepository
    private lateinit var useCase: GetWeatherUseCase

    @BeforeEach
    fun setup() {
        repository = mockk()
        useCase = GetWeatherUseCase(repository)
    }

    @Test
    fun `invoke should return weather data when repository returns data`() = runTest {
        val expectedWeatherData = WeatherData(temperature = 25.0, windSpeed = 5.0)
        coEvery { repository.fetchWeather(52.52, 13.405) } returns expectedWeatherData

        val result = useCase.invoke(52.52, 13.405)

        assertThat(result).isEqualTo(expectedWeatherData)
    }
}
