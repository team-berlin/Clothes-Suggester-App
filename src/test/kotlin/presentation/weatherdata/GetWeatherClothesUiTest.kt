package presentation.weatherdata

import com.berlin.domain.model.WeatherData
import com.berlin.domain.repository.WeatherRepository
import com.berlin.domain.usecase.GetWeatherClothesUseCase
import com.berlin.domain.usecase.GetWeatherUseCase
import com.berlin.presentation.io.Reader
import com.berlin.presentation.io.Viewer
import com.berlin.presentation.weatherdata.GetWeatherClothesUi
import com.berlin.presentation.weatherdata.GetWeatherDataUi
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetWeatherClothesUiTest {
    private lateinit var viewer: Viewer
    private lateinit var reader: Reader
    private lateinit var weatherRepository: WeatherRepository
    private lateinit var getWeatherUseCase: GetWeatherUseCase
    private lateinit var getWeatherClothesUseCase: GetWeatherClothesUseCase
    private lateinit var getWeatherClothesUi: GetWeatherClothesUi
    private lateinit var getWeatherDataUi: GetWeatherDataUi

    @BeforeEach
    fun setup() {
        viewer = mockk(relaxed = true)
        reader = mockk()
        weatherRepository = mockk()
        getWeatherUseCase = GetWeatherUseCase(weatherRepository)
        getWeatherDataUi = GetWeatherDataUi(viewer, reader, getWeatherUseCase)
        getWeatherClothesUseCase = GetWeatherClothesUseCase()
        getWeatherClothesUi = GetWeatherClothesUi(viewer, reader, getWeatherUseCase)
    }

    @Test
    fun `should return when latitude is invalid`() = runTest {
        every { reader.read() } returns IN_VALID_DATA

        getWeatherClothesUi.start()

        verify { viewer.show("Invalid latitude, please enter a number.") }

    }

    @Test
    fun `should return when user back in latitude`() = runTest {
        every { reader.read() } returns USER_BACK

        getWeatherClothesUi.start()

        coVerify(exactly = 0) { getWeatherUseCase.invoke(any(), any()) }
    }

    @Test
    fun `should return when longitude is invalid`() = runTest {
        every { reader.read() } returnsMany listOf("32.0", "invalid")

        getWeatherClothesUi.start()

        verify { viewer.show("Invalid longitude, please enter a number.") }
        coVerify(exactly = 0) { getWeatherUseCase(any(), any()) }
    }

    @Test
    fun `should show weather when coordinates are valid`() = runTest {
        every { reader.read() } returnsMany listOf("32.0", "35.0")
        coEvery { weatherRepository.fetchWeather(32.0, 35.0) } returns WEATHER_DATA

        getWeatherDataUi.start()

        verify {
            viewer.show(match { it.contains("27.5") && it.contains("Code: 2") })
        }

    }

    private companion object {
        val IN_VALID_DATA = "invalid"
        val USER_BACK = "back"
        val WEATHER_DATA = WeatherData(temperature = 27.5, weatherCode = 2, windSpeed = 6.4)
    }
}