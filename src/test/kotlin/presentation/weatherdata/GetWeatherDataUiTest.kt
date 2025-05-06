package presentation.weatherdata

import com.berlin.domain.model.WeatherData
import com.berlin.domain.repository.WeatherRepository
import com.berlin.domain.usecase.GetWeatherUseCase
import com.berlin.presentation.io.Reader
import com.berlin.presentation.io.Viewer
import com.berlin.presentation.weatherdata.GetWeatherDataUi
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class GetWeatherDataUiTest{
        private lateinit var viewer: Viewer
        private lateinit var reader: Reader
        private lateinit var weatherRepository: WeatherRepository
        private lateinit var getWeatherUseCase: GetWeatherUseCase
        private lateinit var getWeatherDataUi: GetWeatherDataUi

        @BeforeEach
        fun setup() {
            viewer = mockk(relaxed = true)
            reader = mockk()
            weatherRepository = mockk()
            getWeatherUseCase = GetWeatherUseCase(weatherRepository)
            getWeatherDataUi = GetWeatherDataUi(viewer, reader, getWeatherUseCase)
        }

        @Test
        fun `should return when latitude is invalid`() = runTest {
            every { reader.read() } returns IN_VALID_DATA

            getWeatherDataUi.start()

            verify { viewer.show("Invalid latitude, please enter a number.") }
        }

        @Test
        fun `should return when user back in latitude`() = runTest {
            every { reader.read() } returns USER_BACK

            getWeatherDataUi.start()

            coVerify(exactly = 0) { getWeatherUseCase.invoke(any(), any()) }
        }

        @Test
        fun `should return when longitude is invalid`() = runTest {
            every { reader.read() } returnsMany listOf("32.0", "invalid")

            getWeatherDataUi.start()

            verify { viewer.show("Invalid longitude, please enter a number.") }
            coVerify(exactly = 0) { getWeatherUseCase(any(), any()) }
        }

    @Test
    fun `test valid latitude and longitude`() = runBlocking {
        // Arrange
        val latitude = 32.61889
        val longitude = 35.79011
        val weatherData = WeatherData(temperature = 20.0, weatherCode = 1, windSpeed = 10.0)

        every { reader.readLatitude() } returns latitude
        every { reader.readLongitude() } returns longitude
        coEvery { getWeatherUseCase(latitude, longitude) } returns weatherData

        // Act
        getWeatherDataUi.start()

        // Assert
        verify { viewer.show("Weather: 20°C, Code: 1, Wind Speed: 10.0 km/h") }
    }



    @Test
        fun `should handle error when weather fetch fails`() = runTest {
            every { reader.read() } returnsMany listOf("32.0", "35.0")
            coEvery { weatherRepository.fetchWeather(32.0, 35.0) } throws Exception("Network error")

            getWeatherDataUi.start()

            verify { viewer.show("Failed to fetch weather: Network error") }
        }
    @Test
    fun `should fetch and show weather data when coordinates are valid`() = runTest {
        every { reader.read() } returnsMany listOf("32.0", "35.0")
        coEvery { weatherRepository.fetchWeather(32.0, 35.0) } returns WEATHER_DATA

        getWeatherDataUi.start()

        verify {
            viewer.show("Weather: 27.5°C, Code: 2")
        }
    }
    @Test
    fun `should return null when user types back for longitude`() = runTest {
        every { reader.read() } returns "back"

        val result = getWeatherDataUi.getLongitude()

        assertNull(result)

        coVerify(exactly = 0) { getWeatherUseCase.invoke(any(), any()) }

        verify { viewer.show("Enter longitude (e.g., 35.79011) or type 'back' to return main menu :") }
    }

    @Test
    fun `should return null when longitude is invalid and user types back after invalid latitude`() = runTest {
        every { reader.read() } returnsMany listOf(IN_VALID_DATA, USER_BACK)

        val result = getWeatherDataUi.getLongitude()

        assertNull(result)

        coVerify(exactly = 0) { getWeatherUseCase.invoke(any(), any()) }

        verify { viewer.show("Invalid longitude, please enter a number.") }
        verify { viewer.show("Enter longitude (e.g., 35.79011) or type 'back' to return main menu :") }
    }

    @Test
    fun `should return null when user types back for both latitude and longitude`() = runTest {
        every { reader.read() } returnsMany listOf(USER_BACK,USER_BACK)

        val result = getWeatherDataUi.getLatitude()

        assertNull(result)

        coVerify(exactly = 0) { getWeatherUseCase.invoke(any(), any()) }

        verify { viewer.show("Enter latitude (e.g., 32.61889) or type 'back' to return main menu :") }
    }
    @Test
    fun `test invalid latitude`() = runBlocking {
        // Arrange
        every { reader.readLatitude() } returns null
        every { reader.readLongitude() } returns 35.79011

        // Act
        getWeatherDataUi.start()

        // Assert
        verify { viewer.show("Invalid latitude, please enter a number.") }
    }


    private companion object {
            val IN_VALID_DATA = "invalid"
            val USER_BACK = "back"
            val WEATHER_DATA = WeatherData(temperature = 27.5, weatherCode = 2, windSpeed = 6.4)
        }
    }

